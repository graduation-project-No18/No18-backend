package graduation.project.no18.global.oauth.auth;

import graduation.project.no18.global.oauth.membertoken.MemberRefreshToken;
import graduation.project.no18.global.oauth.membertoken.MemberRefreshTokenRepository;
import graduation.project.no18.global.oauth.principal.MemberPrincipal;
import graduation.project.no18.global.oauth.properties.AppProperties;
import graduation.project.no18.global.oauth.token.AuthToken;
import graduation.project.no18.global.oauth.token.AuthTokenProvider;
import graduation.project.no18.global.oauth.type.RoleType;
import graduation.project.no18.global.oauth.utils.CookieUtil;
import graduation.project.no18.global.oauth.utils.HeaderUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/   auth")
@RequiredArgsConstructor
public class AuthController {
    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final MemberRefreshTokenRepository memberRefreshTokenRepository;

    private final static long THREE_DAYS_MSEC = 259200000;
    private final static String REFRESH_TOKEN = "refresh_token";

    @PostMapping("/login")
    public ApiResponse login(HttpServletRequest request,
                             HttpServletResponse response,
                             AuthReqDto reqDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        reqDto.getId(),
                        reqDto.getPassword()
                )
        );

        String memberAccountId = reqDto.getId();
        SecurityContextHolder.getContext().setAuthentication(authentication);


        LocalDateTime now = LocalDateTime.now();

        AuthToken accessToken = tokenProvider.createAuthToken(
                memberAccountId,
                ((MemberPrincipal) authentication.getPrincipal()).getRoleType().getCode(),
                now.plusSeconds(appProperties.getAuth().getTokenExpiry())
        );

        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                now.plusSeconds(refreshTokenExpiry)
        );

        MemberRefreshToken memberRefreshToken =
                memberRefreshTokenRepository.findByMemberAccountId(memberAccountId).orElseGet(
                        ()-> memberRefreshTokenRepository
                                .saveAndFlush(new MemberRefreshToken(
                                        memberAccountId,
                                        refreshToken.getToken()))
                );
        memberRefreshToken.updateRefreshToken(refreshToken.getToken());

        int cookieMaxAge = (int) refreshTokenExpiry / 60;
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        return ApiResponse.success("token", accessToken.getToken());
    }

    @GetMapping("/refresh")
    public ApiResponse refreshToken (HttpServletRequest request, HttpServletResponse response) {
        // access token 확인
        String accessToken = HeaderUtil.getAccessToken(request);
        AuthToken authToken = tokenProvider.convertAuthToken(accessToken);
        if (!authToken.validate()) {
            return ApiResponse.invalidAccessToken();
        }

        // expired access token 인지 확인
        Claims claims = authToken.getExpiredTokenClaims();
        if (claims == null) {
            return ApiResponse.notExpiredTokenYet();
        }

        String memberAccountId = claims.getSubject();
        RoleType roleType = RoleType.of(claims.get("role", String.class));

        // refresh token
        String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN)
                .map(Cookie::getValue)
                .orElse((null));
        AuthToken authRefreshToken = tokenProvider.convertAuthToken(refreshToken);

        if (authRefreshToken.validate()) {
            return ApiResponse.invalidRefreshToken();
        }

        // userId refresh token 으로 DB 확인
        MemberRefreshToken memberRefreshToken =
                memberRefreshTokenRepository
                        .findByMemberAccountIdAndRefreshToken(memberAccountId, refreshToken).get();

        if (memberRefreshToken == null) {
            return ApiResponse.invalidRefreshToken();
        }

        LocalDateTime now = LocalDateTime.now();
        AuthToken newAccessToken = tokenProvider.createAuthToken(
                memberAccountId,
                roleType.getCode(),
                now.plusSeconds(appProperties.getAuth().getTokenExpiry())
        );

        long validTime =
                authRefreshToken.getTokenClaims().getExpiration().getTime()
                        - Timestamp.valueOf(now).getTime();


        if (validTime <= THREE_DAYS_MSEC) {
            // refresh 토큰 설정
            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

            authRefreshToken = tokenProvider.createAuthToken(
                    appProperties.getAuth().getTokenSecret(),
                    now.plusSeconds(refreshTokenExpiry)
            );

            // DB에 refresh 토큰 업데이트
            memberRefreshToken.updateRefreshToken(authRefreshToken.getToken());

            int cookieMaxAge = (int) refreshTokenExpiry / 60;
            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
            CookieUtil.addCookie(response, REFRESH_TOKEN, authRefreshToken.getToken(), cookieMaxAge);
        }

        return ApiResponse.success("token", newAccessToken.getToken());
    }

}

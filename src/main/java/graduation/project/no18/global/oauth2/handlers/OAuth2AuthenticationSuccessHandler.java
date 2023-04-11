package graduation.project.no18.global.oauth2.handlers;

import graduation.project.no18.global.oauth2.JwtMemberDetails;
import graduation.project.no18.global.oauth2.exception.BadRequestException;
import graduation.project.no18.global.oauth2.memberauth.MemberRefreshToken;
import graduation.project.no18.global.oauth2.memberauth.MemberRefreshTokenRepository;
import graduation.project.no18.global.oauth2.properties.AppProperties;
import graduation.project.no18.global.oauth2.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import graduation.project.no18.global.oauth2.utils.CookieUtils;
import graduation.project.no18.global.oauth2.utils.JwtTokenUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.REFRESH_TOKEN;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final JwtTokenUtil jwtTokenUtil;

    private final AppProperties appProperties;

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    private final MemberRefreshTokenRepository memberRefreshTokenRepository;

    //oauth2인증이 성공적으로 이뤄졌을 때 실행된다
    //token을 포함한 uri을 생성 후 인증요청 쿠키를 비워주고 redirect 한다.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    //token을 생성하고 이를 포함한 프론트엔드로의 uri를 생성한다.
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        JwtMemberDetails principal = (JwtMemberDetails) authentication.getPrincipal();
        String userId = principal.getName();

        String accessToken = jwtTokenUtil.generateToken(userId);

        long refreshExpiry = appProperties.getAuth().getRefreshTokenExpiry();

        String refreshToken= jwtTokenUtil.generateToken(userId, refreshExpiry);
        MemberRefreshToken memberRefreshToken = memberRefreshTokenRepository.findByUserId(userId);


        if(memberRefreshToken!=null){
            memberRefreshToken.setRefreshToken(refreshToken);
        }
        else{
            memberRefreshToken=new MemberRefreshToken(userId,refreshToken);
            memberRefreshTokenRepository.saveAndFlush(memberRefreshToken);
        }

        int cookieMaxAge = (int) refreshExpiry / 60;
        CookieUtils.deleteCookie(request,response,REFRESH_TOKEN);
        CookieUtils.addCookie(response, REFRESH_TOKEN ,refreshToken,cookieMaxAge);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", accessToken)
                .build().toUriString();
    }

    //인증정보 요청 내역을 쿠키에서 삭제한다.
    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    //application.yml에 등록해놓은 Redirect uri가 맞는지 확인한다. (app.redirect-uris)
    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);
        return appProperties.getOauth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // Only validate host and port. Let the clients use different paths if they want to
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                        return true;
                    }
                    return false;
                });
    }

}
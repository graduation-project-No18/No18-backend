package graduation.project.no18.global.oauth.handler;

import graduation.project.no18.domain.member.Member;
import graduation.project.no18.global.oauth.membertoken.MemberRefreshToken;
import graduation.project.no18.global.oauth.membertoken.MemberRefreshTokenRepository;
import graduation.project.no18.global.oauth.oauth2member.OAuth2MemberInfo;
import graduation.project.no18.global.oauth.oauth2member.OAuth2MemberInfoFactory;
import graduation.project.no18.global.oauth.properties.AppProperties;
import graduation.project.no18.global.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import graduation.project.no18.global.oauth.token.AuthToken;
import graduation.project.no18.global.oauth.token.AuthTokenProvider;
import graduation.project.no18.global.oauth.type.ProviderType;
import graduation.project.no18.global.oauth.type.RoleType;
import graduation.project.no18.global.oauth.utils.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static graduation.project.no18.global.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.REFRESH_TOKEN;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final AuthTokenProvider tokenProvider;
    private final AppProperties appProperties;
    private final MemberRefreshTokenRepository memberRefreshTokenRepository;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        FilterChain chain, Authentication authentication)
            throws IOException, ServletException {

        String targetUrl = determineTargetUrl(request, response, authentication);

        if(response.isCommitted()){
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication){
        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())){
            throw new IllegalArgumentException("매칭되는 redirect URI가 없습니다. 인증을 진행할 수 없습니다.");
        }
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        ProviderType providerType = ProviderType.valueOf(authToken
                .getAuthorizedClientRegistrationId()
                .toUpperCase());

        OidcUser user = ((OidcUser) authentication.getPrincipal());
        OAuth2MemberInfo memberInfo =
                OAuth2MemberInfoFactory.getOAuth2MemberInfo(providerType, user.getAttributes());

        Collection<? extends GrantedAuthority> authorities =
                ((OidcUser) authentication.getPrincipal()).getAuthorities();

        RoleType roleType = hasAuthority(authorities, RoleType.ADMIN.getCode()) ? RoleType.ADMIN : RoleType.MEMBER;

        LocalDateTime now = LocalDateTime.now();

        AuthToken accessToken = tokenProvider.createAuthToken(
                memberInfo.getId(),
                roleType.getCode(),
                now.plusSeconds(appProperties.getAuth().getTokenExpiry())
        );

        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                now.plusSeconds(appProperties.getAuth().getRefreshTokenExpiry())
        );

        MemberRefreshToken memberRefreshToken =
                memberRefreshTokenRepository.findByMemberAccountId(memberInfo.getId()).orElseGet(
                        ()-> memberRefreshTokenRepository.saveAndFlush(
                                new MemberRefreshToken(memberInfo.getId(), refreshToken.getToken()))
                            );
        memberRefreshToken.updateRefreshToken(refreshToken.getToken());

        int cookieMaxAge = (int) refreshTokenExpiry / 60;

        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", accessToken.getToken())
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
    private boolean hasAuthority(Collection<? extends GrantedAuthority> authorities, String authority){
        if(authorities == null){
            return false;
        }

        for(GrantedAuthority grantedAuthority : authorities){
            if(authority.equals(grantedAuthority.getAuthority())){
                return true;
            }
        }
        return false;
    }

    private boolean isAuthorizedRedirectUri(String uri){
        URI clientRedirectUri = URI.create(uri);

        return appProperties.getOAuth2().getAuthorizedRedirectUris().stream()
                .anyMatch(authorizedRedirectUri -> {
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    return compareURI(authorizedURI, clientRedirectUri);
                });
    }

    private boolean compareURI(URI authorizedURI, URI clientURI){
        boolean isSameHost = authorizedURI.getHost().equalsIgnoreCase(clientURI.getHost());
        boolean isSamePort = (authorizedURI.getPort() == clientURI.getPort());

        return (isSameHost && isSamePort);
    }
}

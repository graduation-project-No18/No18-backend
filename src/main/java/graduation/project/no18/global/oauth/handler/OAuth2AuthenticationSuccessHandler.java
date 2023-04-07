package graduation.project.no18.global.oauth.handler;

import graduation.project.no18.global.oauth.membertoken.MemberRefreshTokenRepository;
import graduation.project.no18.global.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import graduation.project.no18.global.oauth.token.AuthTokenProvider;
import graduation.project.no18.global.oauth.utils.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

import static graduation.project.no18.global.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

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

        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
    }
}

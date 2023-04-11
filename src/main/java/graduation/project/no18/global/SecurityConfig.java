package graduation.project.no18.global;


import graduation.project.no18.domain.member.repository.MemberRepository;
import graduation.project.no18.global.oauth2.Filter.JwtRequestFilter;
import graduation.project.no18.global.oauth2.JwtAuthenticationEntryPoint;
import graduation.project.no18.global.oauth2.handlers.OAuth2AuthenticationFailureHandler;
import graduation.project.no18.global.oauth2.handlers.OAuth2AuthenticationSuccessHandler;
import graduation.project.no18.global.oauth2.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import graduation.project.no18.global.oauth2.service.CustomOAuth2MemberService;
import graduation.project.no18.global.oauth2.service.JwtMemberDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final MemberRepository memberRepository;
    private final CustomOAuth2MemberService oAuth2MemberService;
    private final OAuth2AuthenticationSuccessHandler successHandler;
    private final OAuth2AuthenticationFailureHandler failureHandler;
    private final HttpCookieOAuth2AuthorizationRequestRepository oAuth2AuthorizationRequestRepository;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtMemberDetailsService memberDetailsService(){
        return new JwtMemberDetailsService(memberRepository);
    }

    @Bean
    public DaoAuthenticationProvider myDaoAuthenticationProvider(){
        DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
        dap.setUserDetailsService(memberDetailsService());
        return dap;
    }
    @Bean
    public ProviderManager providerManager(){
        return new ProviderManager(myDaoAuthenticationProvider());
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http.cors()
                .and()
                    .csrf()
                        .disable()
                    .formLogin()
                        .disable()
                    .authorizeHttpRequests()
                    .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//                  .requestMatchers("/api/newsletters/**","/api/posts","/swagger-ui/index.html/**","/api/landing/**").permitAll()
//                  .anyRequest().authenticated()
                    .anyRequest().permitAll()
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .oauth2Login()
                    .authorizationEndpoint()
                    .baseUri("/oauth2/authorization")
                    .authorizationRequestRepository(oAuth2AuthorizationRequestRepository)
                .and()
                    .redirectionEndpoint()
                    .baseUri("/*/oauth2/code/*")
                .and()
                    .userInfoEndpoint()
                    .userService(oAuth2MemberService)
                .and()
                    .successHandler(successHandler)
                    .failureHandler(failureHandler);


        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return(web -> web.ignoring().requestMatchers("/profileUploads/**","/messageUploads/**", "/js/**","/webjars/**"));
    }

}

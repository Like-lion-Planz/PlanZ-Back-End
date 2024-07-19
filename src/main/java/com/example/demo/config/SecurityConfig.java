package com.example.demo.config;

import com.example.demo.service.CustomOauth2UserService;
import com.example.demo.util.handler.CustomOauth2AuthenticationSuccessHandler;
import com.example.demo.util.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomOauth2UserService customOauth2UserService;
    @Value("${jwt.secret}")
    private String secretKey;
    private final CustomOauth2AuthenticationSuccessHandler customOauth2AuthenticationSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/", "/api/login/oauth2/kakao", "/api/login/oauth2/google").permitAll()
                        .anyRequest().authenticated()

                )
                .oauth2Login(oauth2 -> oauth2
                        .redirectionEndpoint(redirection -> redirection
                                .baseUri("/api/login/oauth2/google")
                        )
                        .authorizationEndpoint(authorization -> authorization
                                .baseUri("/oauth2/authorization")
                        )
                        .tokenEndpoint(token -> token
                                .accessTokenResponseClient(accessTokenResponseClient())
                        )
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOauth2UserService)
                        )
                        .successHandler(customOauth2AuthenticationSuccessHandler)
                ).logout(logout -> logout
                        .logoutUrl("/api/user/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                )
                .addFilterBefore(new JwtFilter(secretKey), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
        DefaultAuthorizationCodeTokenResponseClient tokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
        tokenResponseClient.setRequestEntityConverter(new CustomRequestEntityConverter());
        return tokenResponseClient;
    }

    private static class CustomRequestEntityConverter extends OAuth2AuthorizationCodeGrantRequestEntityConverter {
        @Override
        public org.springframework.http.RequestEntity<?> convert(OAuth2AuthorizationCodeGrantRequest request) {
            org.springframework.http.RequestEntity<?> entity = super.convert(request);
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.putAll(entity.getHeaders());
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

            org.springframework.util.MultiValueMap<String, String> body = new org.springframework.util.LinkedMultiValueMap<>();
            body.add("client_id", request.getClientRegistration().getClientId());
            body.add("client_secret", request.getClientRegistration().getClientSecret());
            body.add("grant_type", request.getGrantType().getValue());
            body.add("redirect_uri", request.getAuthorizationExchange().getAuthorizationResponse().getRedirectUri());
            body.add("code", request.getAuthorizationExchange().getAuthorizationResponse().getCode());

            return new org.springframework.http.RequestEntity<>(body, headers, entity.getMethod(), entity.getUrl());
        }
    }
}

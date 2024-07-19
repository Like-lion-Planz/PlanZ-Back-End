package com.example.demo.util.handler;

import com.example.demo.util.oauth.CustomOauth2UserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class CustomOauth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOauth2UserDetails userDetails = (CustomOauth2UserDetails) authentication.getPrincipal();
        String tokenJson = objectMapper.writeValueAsString(userDetails.getUserResponseDto());

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(tokenJson);

        clearAuthenticationAttributes(request);
    }
}

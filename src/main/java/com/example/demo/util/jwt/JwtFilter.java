package com.example.demo.util.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final String secretKey;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        log.info("Request URI : {}", requestURI);

        if(requestURI.equals("/") || requestURI.equals("/login") || requestURI.equals("/api/login/oauth2/kakao")
        ){
            filterChain.doFilter(request, response);
            return;
        }

        final String autorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization : {}", autorization);

        if(autorization == null || !autorization.startsWith("Bearer ")){
            log.error("authorization이 잘못 들어옴");
            filterChain.doFilter(request, response);
            return;
        }

        String token = autorization.split(" ")[1];

        if(JwtTokenProvider.isExpired(token, secretKey)){
            log.error("Token 만료");
            filterChain.doFilter(request, response);
            return;
        }

        String user_sub = JwtTokenProvider.getUserSub(token, secretKey);

        String user_name = JwtTokenProvider.getUserName(token, secretKey);

        log.info("sub:{}", user_sub);
        log.info("name:{}", user_name);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user_sub, null, List.of(new SimpleGrantedAuthority("User")));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);

    }
}

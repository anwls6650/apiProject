package com.kmj.apiProject.common.security;

import java.io.IOException;

import jakarta.servlet.Filter;  // ✅ javax.servlet → jakarta.servlet 변경
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class JwtFilter implements Filter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 제거
            String username = jwtUtil.extractClaims(token).get("userId").toString();

            if (jwtUtil.validateToken(token, username)) {
                // 유효한 토큰이면 인증된 사용자로 설정
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(username, null, null);
                SecurityContextHolder.getContext().setAuthentication(auth);  // 인증된 사용자 설정
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}

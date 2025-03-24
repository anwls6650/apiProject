package com.kmj.apiProject.common.security;

import java.io.IOException;

import jakarta.servlet.Filter;  // ✅ javax.servlet → jakarta.servlet 변경
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class JwtFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class); // 로그 추가

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
            int userId = Integer.parseInt(jwtUtil.extractClaims(token).get("userId").toString());

            if (jwtUtil.validateToken(token, userId)) {
                // 유효한 토큰이면 인증된 사용자로 설정
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userId, null, null);
                SecurityContextHolder.getContext().setAuthentication(auth);  // 인증된 사용자 설정

                // 인증 성공 로그 찍기
                logger.info("Authentication successful for userId: {}", userId);
            } else {
                // 토큰이 유효하지 않으면 로그 찍기
                logger.warn("Invalid token for userId: {}", userId);
            }
        } else {
            // Authorization 헤더가 없으면 로그 찍기
            logger.warn("Authorization header is missing or does not start with Bearer");
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}

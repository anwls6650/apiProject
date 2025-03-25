package com.kmj.apiProject.common.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.Filter;  // ✅ javax.servlet → jakarta.servlet 변경
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kmj.apiProject.common.config.ErrorCode;

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
        HttpServletResponse httpResponse = (HttpServletResponse) response;  // HttpServletResponse 추가
        String token = httpRequest.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 제거
            try {
                int userId = Integer.parseInt(jwtUtil.extractClaims(token).get("userId").toString());

                if (jwtUtil.validateToken(token, userId)) {
                    // 유효한 토큰이면 인증된 사용자로 설정
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(userId, null, null);
                    SecurityContextHolder.getContext().setAuthentication(auth);  // 인증된 사용자 설정

                    // 인증 성공 로그 찍기
                    logger.info("Authentication successful for userId: {}", userId);

                    // 유효한 토큰이므로 필터 체인 진행
                    chain.doFilter(request, response);
                } else {
                    // 토큰이 유효하지 않으면 401 Unauthorized 응답
                    logger.warn("Invalid token for userId: {}", userId);

                    // ErrorCode.FAIL로 응답을 Map 형태로 리턴
                    Map<Object, Object> errorResponse = new HashMap<>();
                    errorResponse.putAll(ErrorCode.FAIL.toMap());

                    // JSON으로 변환하여 응답에 설정
                    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    httpResponse.setContentType("application/json");
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.writeValue(httpResponse.getWriter(), errorResponse);
                }
            } catch (Exception e) {
                // 예외가 발생하면 401 Unauthorized 응답
                logger.error("Error during token validation", e);
                sendJsonErrorResponse(httpResponse, ErrorCode.INVALID_TOKEN);
    
            }
        } else {
            // Authorization 헤더가 없거나 잘못된 형식일 경우 401 Unauthorized 응답
            logger.warn("Authorization header is missing or does not start with Bearer");
            sendJsonErrorResponse(httpResponse, ErrorCode.INVALID_TOKEN);
        }
    }
    
    
    
    private void sendJsonErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");  // ✅ UTF-8 설정 추가

        // ErrorCode를 Map으로 변환 후 JSON 변환
        Map<Object, Object> errorResponse = new HashMap<>();
        errorResponse.putAll(errorCode.toMap());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
    
    @Override
    public void destroy() {}
}

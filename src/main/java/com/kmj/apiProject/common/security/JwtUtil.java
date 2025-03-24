package com.kmj.apiProject.common.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final Key key;

    public JwtUtil(@Value("${jwt.secret-key}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // JWT 생성
    public String createToken(String id, String username, int userId) {
        return Jwts.builder()
            .claim("id", id)
            .claim("userId", userId)
            .claim("name", username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1시간 유효
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    // JWT에서 클레임 추출
    public Claims extractClaims(String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (Exception e) {
            // JWT 서명 오류 또는 만료 오류 처리
            throw new SignatureException("Invalid token or token expired.");
        }
    }

    // JWT에서 사용자 ID 추출
    public int extractUserId(String token) {
        return (Integer) extractClaims(token).get("userId");
    }

    // JWT 토큰 검증
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // 토큰이 유효한지 확인
    public boolean validateToken(String token, int id) {
        try {
            int tokenUserId = Integer.parseInt(extractClaims(token).get("userId").toString());
            return (id == tokenUserId && !isTokenExpired(token));
        } catch (Exception e) {
            return false;  // 예외 발생 시 토큰 유효하지 않음
        }
    }
}
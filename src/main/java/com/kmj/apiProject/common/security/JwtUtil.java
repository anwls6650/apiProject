package com.kmj.apiProject.common.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final Key key;

    public JwtUtil(@Value("${jwt.secret-key}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // JWT 생성
    public String createToken(String id, String username) {
        return Jwts.builder()
            .claim("userId", id)
            .claim("name", username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1시간 유효
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    // JWT에서 클레임 추출
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    // JWT에서 사용자 ID 추출
    public String extractUserId(String token) {
    	return extractClaims(token).get("userId").toString();
    }

    // JWT 토큰 검증
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // 토큰이 유효한지 확인
    public boolean validateToken(String token, String id) {
        return (id.equals(extractClaims(token).get("userId")) && !isTokenExpired(token));
    }
}

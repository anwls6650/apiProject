package com.kmj.apiProject.security;

import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private Key key;

    // application.properties
    public JwtUtil(@Value("${jwt.secret-key}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

  //JWT생성
    public String createToken(long id, String name, String role) {
        String token = Jwts.builder()
                .claim("userId",id) 
                .claim("name",name) 
                .claim("role", role)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    public Claims getClamins(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}

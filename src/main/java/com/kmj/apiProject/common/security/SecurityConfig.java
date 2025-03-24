package com.kmj.apiProject.common.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;  // JwtFilter 주입

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors().configurationSource(request -> {
                CorsConfiguration cors = new CorsConfiguration();
                cors.setAllowedOrigins(List.of("http://localhost:8080"));
                cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                cors.setAllowedHeaders(List.of("*"));
                return cors;
            })
            .and()
            .csrf().disable()  // CSRF 보호 비활성화
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/kmj/auth/**/signUp", "/kmj/auth/login").permitAll()  // 회원가입 엔드포인트 허용
                .anyRequest().authenticated()  // 나머지 요청은 인증 필요
            )
            .formLogin(form -> form.disable())  // 기본 로그인 폼 비활성화
            .httpBasic(httpBasic -> httpBasic.disable())
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // ✅ JwtFilter 등록

        return http.build();
    }
}

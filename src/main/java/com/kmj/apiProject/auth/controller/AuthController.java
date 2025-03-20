package com.kmj.apiProject.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kmj.apiProject.auth.dto.AuthDto;
import com.kmj.apiProject.auth.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/kmj/auth")
public class AuthController {

	// SLF4J Logger 생성
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	AuthService loginService;

	@PostMapping("/signUp")
	@ResponseBody
	public Map<Object, Object> signUp(@RequestBody AuthDto loginDto) {

		logger.info("/kmj/signUp : {}", loginDto);

		return loginService.signUp(loginDto);

	}

	@PostMapping("/login")
	@ResponseBody
	public Map<Object, Object> login(@RequestBody AuthDto loginDto) {

		logger.info("/kmj/login : {}", loginDto);

		return loginService.login(loginDto);

	}
	
	@PostMapping("/logout")
	public Map<Object, Object> logout(HttpServletRequest request) {

		logger.info("/kmj/login : {}", request);

		return loginService.logout(request);

	}
}

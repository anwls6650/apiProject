package com.kmj.apiProject.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kmj.apiProject.auth.dto.AuthDto;
import com.kmj.apiProject.auth.dto.DriverDto;
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

	
	/**
	 * 유저 회원가입 
	 * 
	 * @param authDto
	 */
	@PostMapping("/user/signUp")
	@ResponseBody
	public Map<Object, Object> signUp(@RequestBody AuthDto authDto) {

		logger.info("/kmj/auth/user/signUp : {}", authDto);

		return loginService.signUp(authDto);

	}
	
	
	/**
	 * 기사 회원가입 
	 * 
	 * @param authDto
	 */
	@PostMapping("/driver/signUp")
	@ResponseBody
	public Map<Object, Object> driverSignUp(@RequestBody DriverDto driverDto) {

		logger.info("/kmj/auth/driver/signUp : {}", driverDto);

		return loginService.driverSignUp(driverDto);

	}

	@PostMapping("/login")
	@ResponseBody
	public Map<Object, Object> login(@RequestBody AuthDto authDto) {

		logger.info("/kmj/auth/login : {}", authDto);

		return loginService.login(authDto);

	}
	
	@PostMapping("/logout")
	public Map<Object, Object> logout(HttpServletRequest request) {

		logger.info("/kmj/auth/logout : {}", request);

		return loginService.logout(request);

	}
}

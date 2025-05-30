package com.kmj.apiProject.user.controller;

import java.util.HashMap;
import java.util.Map;

import com.kmj.apiProject.user.dto.EntryMethodsDto;
import com.kmj.apiProject.user.dto.UserDeliveryDto;
import com.kmj.apiProject.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kmj.apiProject.auth.dto.AuthDto;
import com.kmj.apiProject.auth.service.AuthService;
import com.kmj.apiProject.common.config.ErrorCode;
import com.kmj.apiProject.common.config.UtilsConfig;
import com.kmj.apiProject.common.security.JwtUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("/kmj/user")
public class UserContoller {

	// SLF4J Logger 생성
	private static final Logger logger = LoggerFactory.getLogger(UserContoller.class);

	@Autowired
	UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthService authService;

	/**
	 * 회원 정보
	 * 
	 * @param id
	 */
	@GetMapping("/userDetail")
	@ResponseBody
	public Map<Object, Object> userDetail() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		int userId = (Integer) authentication.getPrincipal(); // 이미 필터에서 설정된 사용자 ID

		logger.info("/kmj/user/userDetail : {}", userId);

		return userService.userDetail(userId);

	}

	/**
	 * 배송지 등록
	 * 
	 * @param userDeliveryDto
	 */
	@PostMapping("/delivery")
	@ResponseBody
	public Map<Object, Object> delivery(@RequestBody UserDeliveryDto userDeliveryDto) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		int userId = (Integer) authentication.getPrincipal(); // 이미 필터에서 설정된 사용자 ID

		userDeliveryDto.setUserId(userId);

		logger.info("/kmj/user/delivery : {}", userDeliveryDto);

		return userService.delivery(userDeliveryDto);

	}

	/**
	 * 출입방법 리스트
	 * 
	 * @param entryMethodsDto
	 */
	@GetMapping("/access")
	@ResponseBody
	public Map<Object, Object> access(@RequestBody EntryMethodsDto entryMethodsDto) {

		logger.info("/kmj/user/access : {}", entryMethodsDto);

		return userService.access(entryMethodsDto);

	}

}

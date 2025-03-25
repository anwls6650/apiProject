package com.kmj.apiProject.driver.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kmj.apiProject.auth.controller.AuthController;
import com.kmj.apiProject.auth.dto.DriverDto;
import com.kmj.apiProject.auth.service.AuthService;
import com.kmj.apiProject.common.config.ErrorCode;
import com.kmj.apiProject.common.config.UtilsConfig;
import com.kmj.apiProject.common.security.JwtUtil;
import com.kmj.apiProject.driver.service.DriverService;

@Controller
@RequestMapping("/kmj/driver")
public class DriverController {

	// SLF4J Logger 생성
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	DriverService driverService;
	
	@Autowired
	AuthService authService;
	
	@Autowired
	JwtUtil jwtUtil;

	/**
	 * 기사 위치 저장
	 * 
	 * @param DriverDto
	 */
	@PostMapping("/location")
	@ResponseBody
	public Map<Object, Object> location(@RequestBody DriverDto driverDto) {
		

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    int driverId = (Integer) authentication.getPrincipal();  // 이미 필터에서 설정된 사용자 ID

		driverDto.setDriverId(driverId);

		logger.info("/kmj/driver/location : {}", driverDto);

		return driverService.location(driverDto);

	}
	
	/**
	 * 기사 상태 변경
	 * 
	 * @param DriverDto
	 */
	@PostMapping("/status")
	@ResponseBody
	public Map<Object, Object> status(@RequestBody DriverDto driverDto) {
		

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    int driverId = (Integer) authentication.getPrincipal();  // 이미 필터에서 설정된 사용자 ID

		driverDto.setDriverId(driverId);

		logger.info("/kmj/driver/status : {}", driverDto);

		return driverService.status(driverDto);

	}

}

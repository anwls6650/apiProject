package com.kmj.apiProject.order.controller;

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
import com.kmj.apiProject.order.dto.ItemDto;
import com.kmj.apiProject.order.dto.OrderDto;
import com.kmj.apiProject.order.service.OrderService;

@Controller
@RequestMapping("/kmj/order")
public class OrderController {

	// SLF4J Logger 생성
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	OrderService orderService;

	/**
	 * 주문 신청
	 * 
	 * @param OrderDto
	 */
	@PostMapping("/receipt")
	@ResponseBody
	public Map<Object, Object> receipt(@RequestBody OrderDto orderDto) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		int userId = (Integer) authentication.getPrincipal(); // 이미 필터에서 설정된 사용자 ID

		orderDto.setUserId(userId);

		logger.info("/kmj/order/receipt : {}", orderDto);

		return orderService.receipt(orderDto);

	}

	/**
	 * 주문 취소
	 * 
	 * @param OrderDto
	 */
	@PostMapping("/cancel")
	@ResponseBody
	public Map<Object, Object> cancel(@RequestBody OrderDto orderDto) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		int userId = (Integer) authentication.getPrincipal(); // 이미 필터에서 설정된 사용자 ID

		orderDto.setUserId(userId);

		logger.info("/kmj/order/cancel : {}", orderDto);

		return orderService.cancel(orderDto);

	}

	/**
	 * 주문 기사 위치 정보
	 * 
	 * @param orderDto
	 */
	@PostMapping("/location")
	@ResponseBody
	public Map<Object, Object> orderDriverLocation(@RequestBody OrderDto orderDto) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		int userId = (Integer) authentication.getPrincipal(); // 이미 필터에서 설정된 사용자 ID

		orderDto.setUserId(userId);

		logger.info("/kmj/order/location : {}", orderDto);

		return orderService.orderDriverLocation(orderDto);

	}
}

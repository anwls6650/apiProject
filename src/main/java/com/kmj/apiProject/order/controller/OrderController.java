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
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	OrderService orderService;
	

	/**
	 * 주문 신청
	 * 
	 * @param OrderDto
	 */
	@PostMapping("/order")
	@ResponseBody
	public Map<Object, Object> order(@RequestBody OrderDto orderDto) {
		

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    int userId = (Integer) authentication.getPrincipal();  // 이미 필터에서 설정된 사용자 ID

	    orderDto.setUserId(userId);

		logger.info("/kmj/order/order : {}", orderDto);

		return orderService.order(orderDto);

	}
}

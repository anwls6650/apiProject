package com.kmj.apiProject.order.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kmj.apiProject.auth.controller.AuthController;
import com.kmj.apiProject.order.service.OrderService;

@Controller
@RequestMapping("/kmj/order")
public class OrderController {

	// SLF4J Logger 생성
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	OrderService orderService;
	

}

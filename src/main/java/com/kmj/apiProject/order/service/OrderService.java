package com.kmj.apiProject.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kmj.apiProject.order.dao.OrderDao;

@Service
public class OrderService {
	
	@Autowired
	OrderDao orderDao;
	

}

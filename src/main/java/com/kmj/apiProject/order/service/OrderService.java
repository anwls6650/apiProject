package com.kmj.apiProject.order.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kmj.apiProject.auth.dto.DriverDto;
import com.kmj.apiProject.common.config.ErrorCode;
import com.kmj.apiProject.common.config.UtilsConfig;
import com.kmj.apiProject.order.dao.OrderDao;
import com.kmj.apiProject.order.dto.ItemDto;
import com.kmj.apiProject.order.dto.OrderDto;

@Service
public class OrderService {

	@Autowired
	OrderDao orderDao;

	/**
	 * 주문 신청
	 * 
	 * @param itemDto
	 */
	public Map<Object, Object> order(OrderDto orderDto) {
		Map<Object, Object> response = new HashMap<Object, Object>();
		response.putAll(ErrorCode.FAIL.toMap());

		try {

			int order = orderDao.order(orderDto);
			if (order < 0) {
				return response;
			}
			
			for(ItemDto item : orderDto.getItemList()) {
				item.setOrderId(order);
				orderDao.item(item);
			}

			response.putAll(ErrorCode.SUCCESS.toMap());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

}

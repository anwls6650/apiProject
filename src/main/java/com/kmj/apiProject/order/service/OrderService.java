package com.kmj.apiProject.order.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kmj.apiProject.auth.dto.DriverDto;
import com.kmj.apiProject.common.config.ErrorCode;
import com.kmj.apiProject.common.config.UtilsConfig;
import com.kmj.apiProject.order.dao.OrderDao;
import com.kmj.apiProject.order.dto.DeliveryDto;
import com.kmj.apiProject.order.dto.ItemDto;
import com.kmj.apiProject.order.dto.OrderDto;

@Service
public class OrderService {

	@Autowired
	OrderDao orderDao;
	
	@Autowired
    private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
    private ObjectMapper objectMapper;

	/**
	 * 주문 신청
	 * 
	 * @param orderDto
	 */
	public Map<Object, Object> receipt(OrderDto orderDto) {
		Map<Object, Object> response = new HashMap<Object, Object>();
		response.putAll(ErrorCode.FAIL.toMap());

		try {

			int receipt = orderDao.receipt(orderDto);
			if (receipt < 0) {
				return response;
			}

			for (ItemDto item : orderDto.getItemList()) {
				item.setOrderId(receipt);
				orderDao.item(item);
			}

			response.putAll(ErrorCode.SUCCESS.toMap());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 주문 기사 위치 정보
	 * 
	 * @param orderDto
	 */
	public Map<Object, Object> orderDriverLocation(OrderDto orderDto) {
		Map<Object, Object> response = new HashMap<Object, Object>();
		response.putAll(ErrorCode.FAIL.toMap());

		try {
			// 주문 상세 정보 조회
			DeliveryDto orderDetail = orderDao.orderDetail(orderDto);

			// Redis에서 기사 위치 정보 가져오기
			String driverLocationKey = "driver:location" + orderDetail.getDriverId();
			String driverLocationJson = redisTemplate.opsForValue().get(driverLocationKey);

			if (driverLocationJson != null) {
				
				DriverDto driverLocation = objectMapper.readValue(driverLocationJson, DriverDto.class);

				// 성공 응답에 기사 위치 정보 추가
				response.putAll(ErrorCode.SUCCESS.toMap());
				response.put("data", driverLocation);
			} else {
				System.out.println("기사 위치 정보를 찾을 수 없음.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

}

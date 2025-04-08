package com.kmj.apiProject.order.service;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kmj.apiProject.auth.dao.AuthDao;
import com.kmj.apiProject.auth.dto.AuthDto;
import com.kmj.apiProject.auth.dto.DriverDto;
import com.kmj.apiProject.common.config.ErrorCode;
import com.kmj.apiProject.common.config.UtilsConfig;
import com.kmj.apiProject.order.dao.OrderDao;
import com.kmj.apiProject.order.dto.DeliveryDto;
import com.kmj.apiProject.order.dto.ItemDto;
import com.kmj.apiProject.order.dto.OrderDto;
import com.kmj.apiProject.user.dto.UserDto;


@Service
public class OrderService {

	@Autowired
	OrderDao orderDao;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	AuthDao authDao;

	/**
	 * 주문 신청
	 * 
	 * @param orderDto
	 */
	@Transactional
	public Map<Object, Object> receipt(OrderDto orderDto) {
		Map<Object, Object> response = new HashMap<>();
		response.putAll(ErrorCode.FAIL.toMap());

		try {
			int receipt = orderDao.receipt(orderDto);
			if (receipt <= 0) {
				throw new RuntimeException("주문 생성 실패");
			}

			for (ItemDto item : orderDto.getItemList()) {
				item.setOrderId(receipt);
				orderDao.item(item);
			}

			AuthDto authDto = new AuthDto();
			authDto.setUserId(orderDto.getUserId());
			
			AuthDto userLocation = authDao.userDetail(authDto);
			
			 // TODO :고객 주소 기반으로 좌표 변환 예정
			// TODO : 주변 기사 조회 후 mq 요청
			
           

			
			

			response.putAll(ErrorCode.SUCCESS.toMap());

		} catch (Exception e) {
			throw new RuntimeException("주문 처리 중 오류 발생", e); // 트랜잭션이 롤백되도록 예외 발생
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

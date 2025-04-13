package com.kmj.apiProject.order.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kmj.apiProject.auth.dao.AuthDao;
import com.kmj.apiProject.auth.dto.AuthDto;
import com.kmj.apiProject.auth.dto.DriverDto;
import com.kmj.apiProject.common.config.ErrorCode;
import com.kmj.apiProject.common.service.KakaoService;
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

	@Autowired
	AuthDao authDao;

	@Autowired
	private KakaoService kakaoService;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private AmqpAdmin amqpAdmin;

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

			AuthDto userLocation = orderDao.orderUserDetail(authDto);

			// 고객 주소 기반으로 좌표 변환
			Map<String, Double> coordinates = kakaoService.getCoordinatesFromAddress(userLocation.getAddress());
			double px = coordinates.get("PX");
			double py = coordinates.get("PY");

			// 좌표 기준 반경 1km 내 기사 조회
			List<DriverDto> driverLsit = findDriver(py, px, 1.0); // 위도(py), 경도(px)

			// 각 기사에게 RabbitMQ 메시지 전송
			for (DriverDto driver : driverLsit) {
				// DTO 생성 후 데이터 업데이트
				OrderDto dirverOrder = new OrderDto();

				dirverOrder.setPx(px);
				dirverOrder.setPy(py);
				dirverOrder.setOrderId(receipt);

				// JSON 형식
				String jsonLocation = objectMapper.writeValueAsString(dirverOrder);
				String message = objectMapper.writeValueAsString(jsonLocation);

				String queueName = "driver_order_" + driver.getDriverId();
				Queue queue = new Queue(queueName, true);
				amqpAdmin.declareQueue(queue);

				// RabbitMQ에 메시지 전송
				rabbitTemplate.convertAndSend(queueName, message);

			}

			response.putAll(ErrorCode.SUCCESS.toMap());

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("주문 처리 중 오류 발생", e); // 트랜잭션이 롤백되도록 예외 발생
		}

		return response;
	}

	/**
	 * 주문 취소
	 * 
	 * @param orderDto
	 */
	@Transactional
	public Map<Object, Object> cancel(OrderDto orderDto) {
		Map<Object, Object> response = new HashMap<>();
		response.putAll(ErrorCode.FAIL.toMap());

		try {
			int cancel = orderDao.receipt(orderDto);
		


			response.putAll(ErrorCode.SUCCESS.toMap());

		} catch (Exception e) {
			e.printStackTrace();
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

	public List<DriverDto> findDriver(double px, double py, double radiusKm) {
		List<DriverDto> result = new ArrayList<>();
		Set<String> keys = redisTemplate.keys("driver:location:*");
		ObjectMapper objectMapper = new ObjectMapper();

		if (keys == null || keys.isEmpty()) {
			return result;
		}

		for (String key : keys) {
			String json = redisTemplate.opsForValue().get(key);

			if (json == null || json.isBlank()) {
				continue;
			}

			try {
				JsonNode node = objectMapper.readTree(json);

				JsonNode pxNode = node.get("px");
				JsonNode pyNode = node.get("py");
				JsonNode driverIdNode = node.get("driverId");

				if (pxNode == null || pyNode == null || driverIdNode == null) {
					continue;
				}

				double dx = pxNode.asDouble(); // 기사 경도
				double dy = pyNode.asDouble(); // 기사 위도
				long driverId = driverIdNode.asLong();

				double distance = calculateDistance(px, py, dx, dy);
				if (distance <= radiusKm) {
					DriverDto dto = new DriverDto();
					dto.setId(String.valueOf(driverId));
					dto.setPx(dx);
					dto.setPy(dy);
					result.add(dto);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
		final int R = 6371; // 지구 반지름 (km)

		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);

		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return R * c;
	}
}

package com.kmj.apiProject.driver.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kmj.apiProject.auth.dto.DriverDto;
import com.kmj.apiProject.common.config.ErrorCode;
import com.kmj.apiProject.driver.dao.DriverDao;

@Service
public class DriverService {

	@Autowired
	DriverDao driverDao;

	private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public DriverService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = new ObjectMapper(); // ObjectMapper 생성
    }
	private static final String DRIVER_LOCATION_KEY = "driver:location";

	/**
	 * 기사 위치 저장
	 * 
	 * @param DriverDto
	 */ 
	public Map<Object, Object> location(DriverDto driverDto) {
		Map<Object, Object> response = new HashMap<Object, Object>();
		response.putAll(ErrorCode.FAIL.toMap());

		try {

			 // 위치 데이터를 JSON 형식으로 직렬화
            String jsonLocation = objectMapper.writeValueAsString(driverDto);

            // Redis에 저장 (드라이버 위치 저장)
            redisTemplate.opsForValue().set(DRIVER_LOCATION_KEY+driverDto.getDriverId(), jsonLocation);

			response.putAll(ErrorCode.SUCCESS.toMap());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

}

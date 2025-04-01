package com.kmj.apiProject.common.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kmj.apiProject.auth.dto.DriverDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverLocationProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    // 메시지 전송 메소드
    public void sendMessage(DriverDto driverDto) {
        try {
            // DTO를 JSON 문자열로 변환
            String message = objectMapper.writeValueAsString(driverDto);

            // RabbitMQ에 메시지 전송
            rabbitTemplate.convertAndSend("driver_queue", message);  // "driver_queue"는 RabbitMQ 큐 이름
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

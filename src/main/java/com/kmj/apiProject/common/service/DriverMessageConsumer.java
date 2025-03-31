package com.kmj.apiProject.common.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kmj.apiProject.auth.dto.DriverDto;
import com.kmj.apiProject.driver.dao.DriverDao;

@Service
public class DriverMessageConsumer {

    @Autowired
    private DriverDao driverDao;

    @Autowired
    private ObjectMapper objectMapper;

    // @RabbitListener를 사용하여 큐에서 메시지를 자동으로 처리
    @RabbitListener(queues = "driver_queue")  // 큐 이름을 동적으로 설정
    public void receiveMessage(String message) {
        try {
            System.out.println("Received message: " + message);

            // 메시지에서 필요한 데이터 추출
            JsonNode jsonNode = objectMapper.readTree(message);
            int driverId = jsonNode.get("driverId").asInt();
            double px = jsonNode.get("px").asDouble();
            double py = jsonNode.get("py").asDouble();

            // DTO 생성 후 데이터 업데이트
            DriverDto driverDto = new DriverDto();
            driverDto.setPx(px);
            driverDto.setPy(py);
            driverDto.setDriverId(driverId);

            // 위치 업데이트
            driverDao.updateDriverLocation(driverDto);
            System.out.println("DriverLocationUpdated: " + driverId);
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }
}

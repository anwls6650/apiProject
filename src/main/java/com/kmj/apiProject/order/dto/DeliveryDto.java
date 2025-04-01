package com.kmj.apiProject.order.dto;

import lombok.Data;

@Data
public class DeliveryDto {
	
	private int deliveryId;
    private int orderId;
    private int driverId;
    private String status; 
    private String pickupTime;
    private String deliveryTime;
    private String createdAt;

}

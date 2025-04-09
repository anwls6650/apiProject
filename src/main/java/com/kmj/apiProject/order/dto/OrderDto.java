package com.kmj.apiProject.order.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderDto {

	private int orderId; // 주문 고유 ID
	private int userId; // 주문한 사용자 ID
	private int totalPrice; // 총 주문 금액
	private String status; // 주문 상태
	private String createdAt; // 주문 생성일
	private String updatedAt; // 주문 업데이트일
	
	private double px;
	private double py;
	
	private List<ItemDto> itemList;

}

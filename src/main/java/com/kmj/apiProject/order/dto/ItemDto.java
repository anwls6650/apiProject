package com.kmj.apiProject.order.dto;

import lombok.Data;

@Data
public class ItemDto {

	private int itemId;       // 항목 고유 ID
    private int orderId;      // 주문 ID (외래키)
    private String itemName;   // 명칭
    private int itemCnt;  // 개수
    private int price;  // 가격
}

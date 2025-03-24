package com.kmj.apiProject.user.dto;

import lombok.Data;

@Data
public class UserDeliveryDto {
	
	private int userId; // 사용자 고유 ID
    private int entryMethodId; // 출입 방법 ID
    private String deliveryAddress; // 배송지 주소
    private String createdAt; // 등록일 
    private String updatedAt; // 업데이트일
    private String memo; // 공동 현관 메모

}

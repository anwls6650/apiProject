package com.kmj.apiProject.user.dto;

import lombok.Data;

@Data
public class EntryMethodsDto {

	private int entryMethodId; // 출입 방법 고유 ID
	private String methodType; // 출입 방법 타입 (ENUM)
	private String description; // 출입 방법 설명
	private String createdAt; // 생성일
	private String updatedAt; // 업데이트일

}

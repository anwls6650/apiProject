package com.kmj.apiProject.common.config;

import java.util.Map;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // 공통 오류 코드
    SUCCESS("S0000", "정상적으로 처리 되었습니다."),
    FAIL("E9999", "서버 내부 오류가 발생했습니다."),

    
    PARAMETER_FAIL("E9998", "파라미터 오류"),
    
    
    INVALID_REQUEST("E0001", "잘못된 요청입니다."),
    UNAUTHORIZED("E0002", "인증되지 않은 사용자입니다."),
    FORBIDDEN("E0003", "권한이 없습니다."),
    
    // 로그인 관련 오류 코드
    LOGIN_FAILED("L0001", "로그인에 실패했습니다."),
    
    // 회원 가입 관련 오류 코드
    USER_ALREADY_EXISTS("U0001", "이미 존재하는 사용자입니다."),
    
    // 데이터베이스 관련 오류 코드
    DUPLICATE_DATA("D0003", "중복된 데이터입니다."),
    
    USER_NOT_FOUND("U0002", "존재하지 않는 사용자입니다."),
    
    INVALID_TOKEN("T0001", "토큰이 유효하지 않습니다."),
    FAIL_ACCEPTANCE("E9997", "해당 요청은 이미 처리 중입니다.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
    
    // Map 반환 메서드 추가
    public Map<String, String> toMap() {
        return Map.of("code", code, "message", message);
    }
    
    
}

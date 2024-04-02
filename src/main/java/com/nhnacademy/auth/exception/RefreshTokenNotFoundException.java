package com.nhnacademy.auth.exception;

public class RefreshTokenNotFoundException extends RuntimeException {
    public RefreshTokenNotFoundException(String refreshToken) {
        super(refreshToken + "을 찾을 수 없습니다.");
    }
}
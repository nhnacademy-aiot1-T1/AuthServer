package com.nhnacademy.auth.dto;

import lombok.AllArgsConstructor;

/**
 * Login Request 대한 Response Body 내용
 */
@AllArgsConstructor
public class LoginResponse {
    String userId;
    UserRole userRole;
    String accessToken;
    String refreshToken;
}

package com.nhnacademy.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Login Request 대한 Response Body 내용
 */
@Getter
@AllArgsConstructor
public class LoginResponse {
  private Long userId;
  private User.Role role;
  private String accessToken;
}
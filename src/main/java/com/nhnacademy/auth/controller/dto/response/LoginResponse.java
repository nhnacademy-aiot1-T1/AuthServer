package com.nhnacademy.auth.controller.dto.response;

import lombok.Getter;

/**
 * Login Request 대한 Response Body 내용
 */
@Getter
public class LoginResponse {

  private final String accessToken;

  public LoginResponse(String accessToken) {
    this.accessToken = accessToken;
  }
}

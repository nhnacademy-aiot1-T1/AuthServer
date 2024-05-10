package com.nhnacademy.auth.service.dto;

import lombok.Getter;

/**
 *Payco 인증 서버에 요청한 엑세스 토큰을 받기 위한 응답 클래스.
 */
@Getter
public class PaycoAccessTokenResponse {

  private final String accessToken;

  public PaycoAccessTokenResponse(String accessToken) {
    this.accessToken = accessToken;
  }
}

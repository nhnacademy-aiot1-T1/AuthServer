package com.nhnacademy.auth.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 *Payco 인증 서버에 요청한 엑세스 토큰을 받기 위한 응답 클래스.
 */
@Getter
public class PaycoAccessTokenResponse {

  @JsonProperty("access_token")
  private String accessToken;

  @JsonCreator
  public PaycoAccessTokenResponse(String accessToken) {
    this.accessToken = accessToken;
  }
}

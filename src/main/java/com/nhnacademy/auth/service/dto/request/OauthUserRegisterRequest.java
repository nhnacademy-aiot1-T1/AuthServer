package com.nhnacademy.auth.service.dto.request;

import lombok.Builder;
import lombok.Getter;

/**
 * Oauth 최초 가입 요청 시 DB 에 저장할 사용자 정보.
 */
@Getter
public class OauthUserRegisterRequest {

  private final String oauthType;
  private final String oauthId;
  private final String name;
  private final String email;

  @Builder
  private OauthUserRegisterRequest(String oauthType, String oauthId, String name, String email) {
    this.oauthType = oauthType;
    this.oauthId = oauthId;
    this.name = name;
    this.email = email;
  }
}

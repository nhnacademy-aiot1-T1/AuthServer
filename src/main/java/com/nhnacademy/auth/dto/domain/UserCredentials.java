package com.nhnacademy.auth.dto.domain;

import lombok.Builder;
import lombok.Getter;

/**
 * Account api 에서 받을 user 인증에 필요한 정보.
 */
@Getter
public class UserCredentials {

  private final Long id;
  private final String loginId;
  private final String password;

  @Builder
  public UserCredentials(Long id, String loginId, String password) {
    this.id = id;
    this.loginId = loginId;
    this.password = password;
  }
}

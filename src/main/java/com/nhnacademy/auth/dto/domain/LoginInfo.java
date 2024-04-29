package com.nhnacademy.auth.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 로그인 정보 클래스.
 */
@Getter
@AllArgsConstructor
public class LoginInfo {

  private final Long id;
  private final String loginId;
  private final String password;

}
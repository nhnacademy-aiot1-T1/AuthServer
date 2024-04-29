package com.nhnacademy.auth.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 로그인 요청 클래스.
 */
@Getter
@AllArgsConstructor
public class LoginRequest {

  @NotNull
  private final String loginId;

  @NotNull
  private final String password;
}

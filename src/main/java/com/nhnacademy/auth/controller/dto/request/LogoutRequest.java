package com.nhnacademy.auth.controller.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 로그아웃 요청 클래스.
 */
@Getter
@NoArgsConstructor
public class LogoutRequest {

  @NotNull
  private String accessToken;

}

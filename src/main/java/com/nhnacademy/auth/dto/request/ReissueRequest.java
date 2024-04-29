package com.nhnacademy.auth.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;

/**
 * 토큰 재발급 요청 클래스.
 */
@Getter
public class ReissueRequest {

  @NotNull
  private String expiredToken;

}

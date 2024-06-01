package com.nhnacademy.auth.properties;

import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

/**
 * Oauth2 관련 프로퍼티 템플릿 클래스.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Validated
public abstract class Oauth2Properties {

  @NotNull
  private final String clientId;
  @NotNull
  private final String clientSecret;
  @NotNull
  private final String tokenUrl;
  @NotNull
  private final String userInfoUrl;

}

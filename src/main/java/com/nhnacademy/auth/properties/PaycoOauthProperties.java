package com.nhnacademy.auth.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * Payco Oauth 관련 프로퍼티.
 */
@Getter
@ConstructorBinding
@AllArgsConstructor
@ConfigurationProperties(prefix = "payco")
public class PaycoOauthProperties {

  private final String clientId;
  private final String clientSecret;
  private final String tokenUrl;
  private final String tokenPath;
  private final String userInfoUrl;
  private final String userInfoPath;

}



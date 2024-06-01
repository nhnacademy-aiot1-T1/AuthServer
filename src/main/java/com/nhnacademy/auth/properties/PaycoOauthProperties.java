package com.nhnacademy.auth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * Payco Oauth 관련 프로퍼티.
 */
@ConstructorBinding
@ConfigurationProperties(prefix = "payco")
public class PaycoOauthProperties extends Oauth2Properties {

  public PaycoOauthProperties(String clientId, String clientSecret, String tokenUrl,
      String userInfoUrl) {
    super(clientId, clientSecret, tokenUrl, userInfoUrl);
  }
}





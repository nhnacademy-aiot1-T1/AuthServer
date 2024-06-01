package com.nhnacademy.auth.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * JWT 관련 프로퍼티.
 */
@Getter
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

  private final String secret;

}
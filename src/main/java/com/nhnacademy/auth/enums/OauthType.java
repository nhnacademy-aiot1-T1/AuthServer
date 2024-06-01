package com.nhnacademy.auth.enums;


import lombok.Getter;

/**
 * OAuth 타입.
 */
@Getter
public enum OauthType {

  PAYCO("paycoOauthService");

  private final String beanName;

  OauthType(String beanName) {
    this.beanName = beanName;
  }
}

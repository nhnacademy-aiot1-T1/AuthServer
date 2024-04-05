package com.nhnacademy.auth.exception;

/**
 * refresh token을 찾을 수 경우, 발생하는 예외. 지금은 사용하지 않음.
 */

public class AccessTokenNotFoundException extends RuntimeException {

  public AccessTokenNotFoundException() {
    super("AccessToken을 찾을 수 없습니다.");
  }
}
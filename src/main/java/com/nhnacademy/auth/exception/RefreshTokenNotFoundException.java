package com.nhnacademy.auth.exception;

/**
 * refresh token을 찾을 수 경우, 발생하는 예외. 지금은 사용하지 않음.
 */
@Deprecated
public class RefreshTokenNotFoundException extends RuntimeException {

  public RefreshTokenNotFoundException(String refreshToken) {
    super(refreshToken + "을 찾을 수 없습니다.");
  }
}
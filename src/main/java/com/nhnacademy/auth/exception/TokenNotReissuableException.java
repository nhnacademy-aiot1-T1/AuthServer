package com.nhnacademy.auth.exception;

import com.nhnacademy.auth.exception.base.AbstractApiException;
import org.springframework.http.HttpStatus;

/**
 * 토큰이 재발급 불가능한 경우 발생하는 예외
 */
public class TokenNotReissuableException extends AbstractApiException {

  public TokenNotReissuableException() {
    super("재발급 불가능한 토큰입니다.");
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.UNAUTHORIZED;
  }
}

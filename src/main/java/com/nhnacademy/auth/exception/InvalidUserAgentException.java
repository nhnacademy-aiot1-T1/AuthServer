package com.nhnacademy.auth.exception;

import com.nhnacademy.auth.exception.base.AbstractApiException;
import org.springframework.http.HttpStatus;

/**
 * User-Agent 가 잘못된 경우 발생하는 예외
 */
public class InvalidUserAgentException extends AbstractApiException {

  public InvalidUserAgentException(String message) {
    super(message);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.BAD_REQUEST;
  }
}

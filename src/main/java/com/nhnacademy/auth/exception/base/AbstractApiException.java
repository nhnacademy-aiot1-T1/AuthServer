package com.nhnacademy.auth.exception.base;

import org.springframework.http.HttpStatus;

/**
 * 공통 예외.
 */
public abstract class AbstractApiException extends RuntimeException {

  protected AbstractApiException(String message) {
    super(message);
  }

  public abstract HttpStatus getHttpStatus();
}

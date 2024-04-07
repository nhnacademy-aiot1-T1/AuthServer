package com.nhnacademy.auth.exception;

/**
 * jwt token의 만료 시간이 현재 시간을 넘겼을 경우 발생하는 예외.
 */
public class ExpiredJwtTokenException extends RuntimeException {

  public ExpiredJwtTokenException() {
    super("Expired JWT token");
  }

}

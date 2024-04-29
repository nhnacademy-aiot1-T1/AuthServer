package com.nhnacademy.auth.exception;


import com.nhnacademy.auth.exception.base.AbstractApiException;
import org.springframework.http.HttpStatus;

/**
 * 토큰 발급 시 User-Agent 가 요청 시의 User-Agent 와 다를 경우 발생하는 예외.
 */
public class UserAgentMismatchException extends AbstractApiException {

  public UserAgentMismatchException() {
    super("토큰 발급 시 사용자 정보와 요청 시 사용자 정보가 일치하지 않습니다.");
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.UNAUTHORIZED;
  }
}

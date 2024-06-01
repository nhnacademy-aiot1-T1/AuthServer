package com.nhnacademy.auth.exception;

import com.nhnacademy.auth.exception.base.AbstractApiException;
import org.springframework.http.HttpStatus;

/**
 * 데이터베이스의 password와 로그인 요청 password가 다를 경우, 발생하는 예외.
 */
public class PasswordNotMatchException extends AbstractApiException {

  public PasswordNotMatchException(String message) {
    super("비밀번호를 다시 확인해주십시오." + message);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.UNAUTHORIZED;
  }
}
package com.nhnacademy.auth.exception;

import com.nhnacademy.auth.exception.base.AbstractApiException;
import org.springframework.http.HttpStatus;

/**
 * user Id를 찾을 수 없을 경우, 발생하는 예외.
 */
public class UserNotFoundException extends AbstractApiException {


  public UserNotFoundException() {
    super("해당 사용자가 존재하지 않습니다.");
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.NOT_FOUND;
  }
}

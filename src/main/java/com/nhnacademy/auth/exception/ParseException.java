package com.nhnacademy.auth.exception;

import com.nhnacademy.auth.exception.base.AbstractApiException;
import org.springframework.http.HttpStatus;

public class ParseException extends AbstractApiException {

  public ParseException() {
    super("구문 분석 오류가 발생했습니다.");
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.BAD_REQUEST;
  }
}

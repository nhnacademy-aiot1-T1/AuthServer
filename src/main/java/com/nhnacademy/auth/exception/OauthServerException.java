package com.nhnacademy.auth.exception;

import com.nhnacademy.auth.exception.base.AbstractApiException;
import org.springframework.http.HttpStatus;

public class OauthServerException extends AbstractApiException {

  public OauthServerException(String message) {
    super("Oauth 서버에서 오류가 발생했습니다 : " + message);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.SERVICE_UNAVAILABLE;
  }
}

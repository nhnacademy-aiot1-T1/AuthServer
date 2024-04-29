package com.nhnacademy.auth.exception;

import com.nhnacademy.auth.exception.base.AbstractApiException;
import org.springframework.http.HttpStatus;

/**
 * FeignClient 호출 중 오류가 발생했을 때 발생하는 예외.
 */
public class FeignClientException extends AbstractApiException {

  public FeignClientException(String url) {
    super("FeignClient 호출 중 오류가 발생했습니다. : " + url);
  }

  public HttpStatus getHttpStatus() {
    return HttpStatus.SERVICE_UNAVAILABLE;
  }
}

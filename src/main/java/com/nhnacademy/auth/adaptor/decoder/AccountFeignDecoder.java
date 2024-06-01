package com.nhnacademy.auth.adaptor.decoder;

import com.nhnacademy.auth.exception.FeignClientException;
import feign.Response;
import feign.codec.ErrorDecoder;

/**
 * Account 서비스를 호출하는 FeignClient 응답 예외를 처리하는 클래스.
 */
public class AccountFeignDecoder implements ErrorDecoder {

  @Override
  public Exception decode(String methodKey, Response response) {

    String url = response.request().url();
    int statusCode = response.status();
    String message = response.reason();

    return new FeignClientException(url, statusCode, message);
  }
}

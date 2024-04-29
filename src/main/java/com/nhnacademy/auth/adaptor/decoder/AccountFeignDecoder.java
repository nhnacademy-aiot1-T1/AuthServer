package com.nhnacademy.auth.adaptor.decoder;

import com.nhnacademy.auth.exception.FeignClientException;
import com.nhnacademy.auth.exception.UserNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

/**
 * Account 서비스를 호출하는 FeignClient 응답 예외를 처리하는 클래스.
 */
public class AccountFeignDecoder implements ErrorDecoder {

  @Override
  public Exception decode(String methodKey, Response response) {
    HttpStatus responseStatus = HttpStatus.valueOf(response.status());
    String url = response.request().url();

    if (responseStatus == HttpStatus.NOT_FOUND) {
      throw new UserNotFoundException();
    }
    return new FeignClientException(url);
  }
}

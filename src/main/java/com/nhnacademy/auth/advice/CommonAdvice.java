package com.nhnacademy.auth.advice;

import com.nhnacademy.auth.exception.base.AbstractApiException;
import com.nhnacademy.common.dto.CommonResponse;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 공통 예외 처리 클래스.
 */
@RestControllerAdvice
public class CommonAdvice {

  /**
   * 추상 클래스인 AbstractApiException 을 상속 받은 예외 처리.
   *
   * @param exception 예외
   * @param response  응답
   * @return CommonResponse
   */
  @ExceptionHandler(AbstractApiException.class)
  public CommonResponse<String> apiExceptionHandler(
      final AbstractApiException exception, HttpServletResponse response) {
    int httpStatus = exception.getHttpStatus().value();
    response.setStatus(httpStatus);
    return CommonResponse.fail(exception.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public CommonResponse<String> methodArgumentNotValidExceptionHandler(
      final MethodArgumentNotValidException e) {
    return CommonResponse.fail(e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public CommonResponse<String> unknownExceptionHandler(
      final Exception e) {
    return CommonResponse.fail(e.getMessage());
  }
}

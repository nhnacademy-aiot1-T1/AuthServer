package com.nhnacademy.auth.advice;

import com.nhnacademy.auth.exception.AccessTokenNotFoundException;
import com.nhnacademy.auth.exception.IpIsNotEqualsException;
import com.nhnacademy.auth.exception.PasswordNotMatchException;
import com.nhnacademy.auth.exception.UserIdNotFoundException;
import com.nhnacademy.common.dto.CommonResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 현재 exception handler만 하는 RestControllerAdvice.
 * <p>
 * RuntimeException으로 통일. httpStatus가 401로 동일.
 * <li>5종의 예외 핸들링 중.
 * </p>
 */
@RestControllerAdvice
public class CommonAdvice {

  @ExceptionHandler(value = {PasswordNotMatchException.class,
      UserIdNotFoundException.class,
      IpIsNotEqualsException.class,
      AccessTokenNotFoundException.class,
      ExpiredJwtException.class})
  public ResponseEntity<CommonResponse<RuntimeException>> authorizationFailureHandler(RuntimeException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(CommonResponse.fail(e.getMessage()));
  }
}

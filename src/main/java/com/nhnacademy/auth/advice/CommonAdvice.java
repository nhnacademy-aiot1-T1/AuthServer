package com.nhnacademy.auth.advice;

import com.nhnacademy.auth.dto.CommonResponse;
import com.nhnacademy.auth.exception.IpIsNotEqualsException;
import com.nhnacademy.auth.exception.PasswordNotMatchException;
import com.nhnacademy.auth.exception.RefreshTokenNotFoundException;
import com.nhnacademy.auth.exception.UserIdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 현재 exception handler만 하는 RestControllerAdvice.
 * <p>
 * 3종의 예외 핸들링 중.
 * </p>
 */
@RestControllerAdvice
public class CommonAdvice {

  @ExceptionHandler(PasswordNotMatchException.class)
  public ResponseEntity<CommonResponse<PasswordNotMatchException>> passwordNotMatch() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(CommonResponse.fail("password가 일치하지 않습니다."));
  }

  @ExceptionHandler(UserIdNotFoundException.class)
  public ResponseEntity<CommonResponse<UserIdNotFoundException>> userIdNotFound() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(CommonResponse.fail("아이디를 찾을 수 없습니다."));
  }

  @ExceptionHandler(IpIsNotEqualsException.class)
  public ResponseEntity<CommonResponse<IpIsNotEqualsException>> accessTokenIsNotEqual() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(CommonResponse.fail("access token이 일치하지 않습니다."));
  }
}

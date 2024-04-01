package com.nhnacademy.auth.advice;

import com.nhnacademy.auth.dto.LoginResponse;
import com.nhnacademy.auth.dto.ResponseFormat;
import com.nhnacademy.auth.exception.RefreshTokenNotFoundException;
import com.nhnacademy.auth.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CommonAdvice {

    /**
     * 로그인에 실패했을 때에 대한 Aop
     * @param userNotFoundException
     * @return Http status : 401
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseFormat> userNotFound(UserNotFoundException userNotFoundException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ResponseFormat.builder()
                        .status("fail")
                        .data(null)
                        .message("로그인에 실파하였습니다.")
                        .localDateTime(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<ResponseFormat> refreshTokenNotFound (RefreshTokenNotFoundException refreshTokenNotFoundException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ResponseFormat.builder()
                        .status("fail")
                        .data(null)
                        .message("refresh token의 인증에 실패하여, access token의 생성이 불가능합니다.")
                        .localDateTime(LocalDateTime.now())
                        .build());
    }
}

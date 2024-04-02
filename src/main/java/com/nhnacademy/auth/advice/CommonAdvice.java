package com.nhnacademy.auth.advice;

import com.nhnacademy.auth.dto.ResponseFormat;
import com.nhnacademy.auth.exception.RefreshTokenNotFoundException;
import com.nhnacademy.auth.exception.PasswordNotMatchException;
import com.nhnacademy.auth.exception.UserIdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CommonAdvice {

    private static final String FAIL = "fail";

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<ResponseFormat> passwordNotMatch(PasswordNotMatchException passwordNotMatchException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ResponseFormat.builder()
                        .status(FAIL)
                        .data(passwordNotMatchException)
                        .message("password가 일치하지 않습니다.")
                        .localDateTime(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<ResponseFormat> refreshTokenNotFound (RefreshTokenNotFoundException refreshTokenNotFoundException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ResponseFormat.builder()
                        .status(FAIL)
                        .data(refreshTokenNotFoundException)
                        .message("refresh token의 인증에 실패하여, access token의 생성이 불가능합니다.")
                        .localDateTime(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<ResponseFormat> userIdNotFound (UserIdNotFoundException userIdNotFoundException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ResponseFormat.builder()
                        .status(FAIL)
                        .data(userIdNotFoundException)
                        .message("아이디를 찾을 수 없습니다..")
                        .localDateTime(LocalDateTime.now())
                        .build());
    }
}

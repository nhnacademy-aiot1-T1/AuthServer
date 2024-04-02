package com.nhnacademy.auth.advice;

import com.nhnacademy.auth.dto.Response;
import com.nhnacademy.auth.exception.PasswordNotMatchException;
import com.nhnacademy.auth.exception.RefreshTokenNotFoundException;
import com.nhnacademy.auth.exception.UserIdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CommonAdvice {

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<Response<PasswordNotMatchException>> passwordNotMatch(PasswordNotMatchException passwordNotMatchException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Response.fail(passwordNotMatchException, "password가 일치하지 않습니다."));
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<Response<RefreshTokenNotFoundException>> refreshTokenNotFound (RefreshTokenNotFoundException refreshTokenNotFoundException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Response.fail(refreshTokenNotFoundException, "refresh token의 인증에 실패하여, access token의 생성이 불가능합니다."));
    }

    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<Response<UserIdNotFoundException>> userIdNotFound (UserIdNotFoundException userIdNotFoundException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Response.fail(userIdNotFoundException, "아이디를 찾을 수 없습니다.."));
    }
}

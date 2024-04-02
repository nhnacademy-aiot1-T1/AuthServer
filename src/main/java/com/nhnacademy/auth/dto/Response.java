package com.nhnacademy.auth.dto;

import java.time.LocalDateTime;
import lombok.Getter;

/**
 * 서버 기본 HTTP 응답 포맷 내용입니다
 *
 * status - HTTP 상태메세지와 별개의 api 상태 메세지
 * data - response body 내용
 * message - 전달할 내용
 * localDateTime - 전달 시각
 */
@Getter
public class Response<T> {
    private final String status;
    private final T data;
    private final String message;
    private final LocalDateTime timestamp;

    private Response(String status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> Response<T> success(T data, String message) {
        return new Response<>("success", data, message);
    }

    public static <T> Response<T> fail(T data, String message) {
        return new Response<>("fail", data, message);
    }
}
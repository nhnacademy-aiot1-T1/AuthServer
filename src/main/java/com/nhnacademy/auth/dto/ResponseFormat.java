package com.nhnacademy.auth.dto;

import java.time.LocalDateTime;
import lombok.Builder;

/**
 * 서버 기본 HTTP 응답 포맷 내용입니다
 *
 * status - HTTP 상태메세지와 별개의 api 상태 메세지
 * data - response body 내용
 * message - 전달할 내용
 * localDateTime - 전달 시각
 */
@Builder
public class ResponseFormat<T> {
  private String status;
  private T data;
  private String message;
  private LocalDateTime localDateTime;
}

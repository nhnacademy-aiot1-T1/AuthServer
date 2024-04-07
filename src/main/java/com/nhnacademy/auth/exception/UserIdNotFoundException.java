package com.nhnacademy.auth.exception;

/**
 * user Id를 찾을 수 없을 경우, 발생하는 예외.
 */
public class UserIdNotFoundException extends RuntimeException {

  public UserIdNotFoundException() {
    super("아이디를 찾을 수 없습니다.");
  }
}

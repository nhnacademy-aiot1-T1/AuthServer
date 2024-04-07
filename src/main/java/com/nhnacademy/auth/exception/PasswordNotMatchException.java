package com.nhnacademy.auth.exception;

/**
 * password가 다를 경우, 발생하는 예외.
 */
public class PasswordNotMatchException extends RuntimeException {

  public PasswordNotMatchException(String message) {
    super("비밀번호를 다시 확인해주십시오." + message);
  }
}
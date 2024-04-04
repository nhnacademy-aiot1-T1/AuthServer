package com.nhnacademy.auth.exception;


public class IpIsNotEqualsException extends RuntimeException {

  public IpIsNotEqualsException() {
    super("access token이 일치하지 않습니다. 다시 로그인 해주세요.");
  }
}

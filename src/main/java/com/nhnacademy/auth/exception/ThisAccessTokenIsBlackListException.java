package com.nhnacademy.auth.exception;

public class ThisAccessTokenIsBlackListException extends RuntimeException {

  public ThisAccessTokenIsBlackListException() {
    super("This access token is blacklisted");
  }
}

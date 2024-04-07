package com.nhnacademy.auth.exception;

public class OAuthAuthorizationFailException extends RuntimeException {
    private static final String MESSAGE = "oauth 인증에 실패했습니다.";

    public OAuthAuthorizationFailException() {
        super(MESSAGE);
    }
}

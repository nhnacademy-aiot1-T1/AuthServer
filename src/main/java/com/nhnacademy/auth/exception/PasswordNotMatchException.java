package com.nhnacademy.auth.exception;

public class PasswordNotMatchException extends RuntimeException{
    public PasswordNotMatchException(String message) {
        super("비밀번호를 다시 확인해주십시오." + message);
    }
}

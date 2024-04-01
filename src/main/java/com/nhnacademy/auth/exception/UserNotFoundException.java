package com.nhnacademy.auth.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super("아이디 혹은 비밀번호를 다시 확인해주십시오." + message);
    }
}

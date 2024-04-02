package com.nhnacademy.auth.exception;

public class UserIdNotFoundException extends RuntimeException{
    public UserIdNotFoundException() {
        super("아이디를 찾을 수 없습니다.");
    }
}

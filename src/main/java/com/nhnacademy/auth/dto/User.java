package com.nhnacademy.auth.dto;

import lombok.Getter;

/**
 * Account api에서 받을 user 정보
 */
@Getter
public class User {
    private String id;
    private Status status;
    private Role role;

    public enum Role {
        ADMIN,
        USER
    }

    public enum Status {
        ACTIVE,
        DEACTIVATE
    }
}
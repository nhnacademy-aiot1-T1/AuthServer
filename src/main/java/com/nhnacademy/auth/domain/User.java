package com.nhnacademy.auth.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@RequiredArgsConstructor
public class User {
    @Id
    private String userId;
    private String userPassword;

    private UserStatus userStatus;

    private UserRole userRole;

    public static enum UserStatus {

    }

    public static enum UserRole {
        ROLE_ADMIN("관리자"),
        ROLE_USER("회원");

        UserRole(String role) {
        }
    }
}

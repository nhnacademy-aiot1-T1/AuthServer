package com.nhnacademy.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Account api에서 받을 user 정보
 */
@Getter
@AllArgsConstructor
public class User {
    String id;
    UserStatus status;
    UserRole role;
}
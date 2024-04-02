package com.nhnacademy.auth.dto;

import lombok.Getter;

/**
 * Account api에서 받을 user 정보
 */
@Getter
public class User {
    String userId;
    UserRole userRole;
}
package com.nhnacademy.auth.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDto {
    private String userId;

    private String userPassword;

    private UserState userState;

    private UserRole userRole;


}

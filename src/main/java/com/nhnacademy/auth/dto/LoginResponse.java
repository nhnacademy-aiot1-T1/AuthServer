package com.nhnacademy.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class LoginResponse {
    private UserDto userDto;

    private String accessToken;

    private String refreshToken;
}

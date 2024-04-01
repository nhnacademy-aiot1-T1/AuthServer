package com.nhnacademy.auth.controller;

import com.nhnacademy.auth.dto.LoginResponse;
import com.nhnacademy.auth.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface AuthController {
    ResponseEntity<LoginResponse> generateAccessToken(UserDto userDto);

    ResponseEntity<String> regenerageAccessToken(String userId);
}

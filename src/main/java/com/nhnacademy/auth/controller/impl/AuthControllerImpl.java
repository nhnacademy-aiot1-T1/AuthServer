package com.nhnacademy.auth.controller.impl;

import com.nhnacademy.auth.controller.AuthController;
import com.nhnacademy.auth.dto.LoginResponse;
import com.nhnacademy.auth.dto.UserDto;
import com.nhnacademy.auth.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthControllerImpl implements AuthController {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @PostMapping("/accessToken")
    public ResponseEntity<LoginResponse> generateAccessToken(UserDto userDto) {

        String accessToken = jwtTokenProvider.generateAccessToken(userDto.getUserId());
//        String refreshToken = jwtTokenProvider.generateRefreshToken(userDto.getUserId());
        String refreshToken = "12345";
        LoginResponse loginResponse = new LoginResponse(userDto, accessToken, refreshToken);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Content-Type", "application/json")
                .body(loginResponse);
    }

    @Override
    @PostMapping("/regenerateToken")
    public ResponseEntity<String> regenerageAccessToken(String userId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jwtTokenProvider.regenerateAccessToken(userId));
    }
}

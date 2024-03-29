package com.nhnacademy.auth.controller.impl;

import com.nhnacademy.auth.controller.AuthController;
import com.nhnacademy.auth.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public ResponseEntity<String> generateAccessToken(String userId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jwtTokenProvider.generateAccessToken(userId));
    }

    @Override
    public ResponseEntity<String> generateRefreshToken(String userId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jwtTokenProvider.generateRefreshToken(userId));
    }

    @Override
    public ResponseEntity<String> regenerageAccessToken(String userId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jwtTokenProvider.regenerateAccessToken(userId));
    }
}

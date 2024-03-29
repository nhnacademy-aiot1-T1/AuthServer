package com.nhnacademy.auth.controller;

import org.springframework.http.ResponseEntity;

public interface AuthController {
    ResponseEntity<String> generateAccessToken(String userId);

    ResponseEntity<String> generateRefreshToken(String userId);

    ResponseEntity<String> regenerageAccessToken(String userId);
}

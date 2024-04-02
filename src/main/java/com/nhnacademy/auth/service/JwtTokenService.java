package com.nhnacademy.auth.service;

public interface JwtTokenService {
    String generateAccessToken(String userId);

    String generateRefreshToken(String userId);

    String createJwtToken (String userId, int expiredTime);

    String regenerateAccessToken(String refreshToken);

    boolean checkRefreshToken(String refreshToken);
}

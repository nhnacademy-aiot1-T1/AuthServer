package com.nhnacademy.auth.service;

import com.nhnacademy.auth.dto.User;

public interface JwtTokenService {

  String generateAccessToken(User user, String ip);

  String createJwtToken(String userId, String userRole, String ip, int expiredTime);

  String generateJwtTokenFromMobile(User user, String ip);

  String regenerateAccessToken(String nowIp, String legacyAccessToken);
}

package com.nhnacademy.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.nhnacademy.auth.dto.User;
import java.io.IOException;

public interface JwtTokenService {

  String generateAccessToken(User user, String ip, String browser);

  String createJwtToken(String userId, int expiredTime);

  String generateJwtTokenFromMobile(User user, String ip, String browser) throws IOException, GeoIp2Exception;

  String regenerateAccessToken(String nowIp, String browser,String legacyAccessToken)
      throws JsonProcessingException;

  String getExpiredTimeFromJwtToken(String token) throws JsonProcessingException;

  String getUserIdFromJwtToken(String token) throws JsonProcessingException;
}

package com.nhnacademy.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.nhnacademy.auth.dto.User;
import java.io.IOException;

public interface JwtTokenService {

  String issueAndSaveAccessToken(User user);

  String createJwt(Long userId, int expiredTime);

  String generateJwtFromMobile(User user) throws IOException, GeoIp2Exception;

  String regenerateAccessToken(String nowIp, String browser,String legacyAccessToken)
      throws JsonProcessingException;

  String getExpiredTimeFromJwt(String token) throws JsonProcessingException;

  String getUserIdFromJwt(String token) throws JsonProcessingException;
}

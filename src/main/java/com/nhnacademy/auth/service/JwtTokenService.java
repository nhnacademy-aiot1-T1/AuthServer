package com.nhnacademy.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.nhnacademy.auth.dto.User;
import java.io.IOException;

public interface JwtTokenService {

  String generateAccessToken(User user, String ip);

  String createJwtToken(String userId, String userRole, String ip, int expiredTime);

  String generateJwtTokenFromMobile(User user, String ip) throws IOException, GeoIp2Exception;

  String regenerateAccessToken(String nowIp, String legacyAccessToken)
      throws JsonProcessingException;
}

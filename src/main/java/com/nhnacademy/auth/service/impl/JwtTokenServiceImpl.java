package com.nhnacademy.auth.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.Serializers.Base;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.nhnacademy.auth.dto.LoginResponse;
import com.nhnacademy.auth.dto.User;
import com.nhnacademy.auth.dto.testDto;
import com.nhnacademy.auth.exception.AccessTokenNotFoundException;
import com.nhnacademy.auth.exception.IpIsNotEqualsException;
import com.nhnacademy.auth.properties.JwtProperties;
import com.nhnacademy.auth.service.AccessTokenService;
import com.nhnacademy.auth.service.IpGeolationService;
import com.nhnacademy.auth.service.JwtTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.netty.handler.codec.base64.Base64Decoder;
import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Decoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;

/**
 * UserId를 받아, JwtToken을 발급하는 Service layer
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

  private final JwtProperties jwtProperties;

  private final IpGeolationService ipGeolationService;

  private final AccessTokenService accessTokenService;

  private static final int EXPIRED_TIME_MINUTE = 50;

  @Override
  public String generateAccessToken(User user, String ip) {
    String accessToken = createJwtToken(user.getId(), user.getRole().toString(), ip,
        EXPIRED_TIME_MINUTE);
    accessTokenService.saveAccessToken(accessToken, ip);
    return accessToken;
  }

  /**
   * 실질적인 발급 로직.
   *
   * @param userId      : token 발급 대상.
   * @param expiredTime : 만료 시간. 토큰의 종류에 따라 만료 시간이 다름.
   * @return string : JwtToken
   */
  @Override
  public String createJwtToken(String userId, String userRole, String ip, int expiredTime) {
    String encodeIp = DigestUtils.sha256Hex(ip);

    Claims claims = Jwts.claims();
    claims.put("userId", userId);
    claims.put("userRole", userRole);
    claims.put("userIp", encodeIp);

    log.warn("claims", claims);
    Date now = new Date();

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(DateUtils.addMinutes(now, expiredTime))
        .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
        .compact();
  }

  @Override
  public String generateJwtTokenFromMobile(User user, String ip)
      throws IOException, GeoIp2Exception {
    String mobileIpCountry = ipGeolationService.getContury(ip);
    return generateAccessToken(user, mobileIpCountry);
  }

  @Override
  public String regenerateAccessToken(String nowIp, String legacyAccessToken)
      throws JsonProcessingException {

    String encodeIp = DigestUtils.sha256Hex(nowIp);
    String userId = getUserIdFromJwtToken(legacyAccessToken);
    String userRole = getUserRoleFromJwtToken(legacyAccessToken);
    String userIp = getEncodeIpFromJwtToken(legacyAccessToken);

    if (checkAccessTokenIp(userIp, encodeIp)) {
      String newAccessToken = createJwtToken(userId, userRole, userIp, EXPIRED_TIME_MINUTE);
      if (accessTokenService.findAccessToken(legacyAccessToken)) {
        accessTokenService.updateAccessToken(legacyAccessToken, newAccessToken);
        return newAccessToken;
      } else {
        throw new AccessTokenNotFoundException();
      }
    }
    throw new IpIsNotEqualsException();
  }


  private boolean checkAccessTokenIp(String legacyIp, String nowIp) {
    return legacyIp.equals(nowIp);
  }

  private String getEncodeIpFromJwtToken(String token) throws JsonProcessingException {
    return parsing(token).getUserIp();
  }

  private String getUserIdFromJwtToken(String token) throws JsonProcessingException {
    return parsing(token).getUserId();
  }

  private String getUserRoleFromJwtToken(String token) throws JsonProcessingException {
    return parsing(token).getUserRole();
  }

  private testDto parsing(String token) throws JsonProcessingException {
    String encodePayload = token.split("\\.")[1];
    String decode= new String(Base64.getDecoder().decode(encodePayload));
    ObjectMapper objectMapper = new ObjectMapper();
    testDto a = objectMapper.readValue(decode, testDto.class);
    return a;
  }
}
package com.nhnacademy.auth.service.impl;

import com.nhnacademy.auth.dto.User;
import com.nhnacademy.auth.exception.IpIsNotEqualsException;
import com.nhnacademy.auth.properties.JwtProperties;
import com.nhnacademy.auth.service.IpGeolationService;
import com.nhnacademy.auth.service.JwtTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private static final int EXPIRED_TIME_MINUTE = 5;


  @Override
  public String generateAccessToken(User user, String ip) {
    return createJwtToken(user.getId(), user.getRole().toString(), ip, EXPIRED_TIME_MINUTE);
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
    String encodeIp = bCryptPasswordEncoder.encode(ip);

    Claims claims = Jwts.claims();
    claims.put("userId", userId);
    claims.put("userRole", userRole);
    claims.put("userIp", encodeIp);

    log.error("claims", claims);
    Date now = new Date();

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(DateUtils.addMinutes(now, expiredTime))
        .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
        .compact();
  }

  @Override
  public String generateJwtTokenFromMobile(User user, String ip) {
    String mobileIpCountry = ipGeolationService.getContury(ip);
    return generateAccessToken(user, mobileIpCountry);
  }

  @Override
  public String regenerateAccessToken(String nowIp, String legacyAccessToken) {
    String userId = getUserIdFromJwtToken(legacyAccessToken);
    String userRole = getUserRoleFromJwtToken(legacyAccessToken);
    String userIp = getEncodeIpFromJwtToken(legacyAccessToken);

    if (checkAccessTokenIp(userIp, nowIp)) {
      return createJwtToken(userId, userRole, userIp, EXPIRED_TIME_MINUTE);
    }
    throw new IpIsNotEqualsException();
  }

  private boolean checkAccessTokenIp(String legacyIp, String nowIp) {
    return legacyIp.equals(nowIp);
  }

  private String getEncodeIpFromJwtToken(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(jwtProperties.getSecret())
        .parseClaimsJws(token)
        .getBody();

    return claims.get("userIp", String.class);
  }

  private String getUserIdFromJwtToken(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(jwtProperties.getSecret())
        .parseClaimsJws(token)
        .getBody();

    return claims.get("userId", String.class);
  }

  private String getUserRoleFromJwtToken(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(jwtProperties.getSecret())
        .parseClaimsJws(token)
        .getBody();

    return claims.get("userRole", String.class);
  }
}
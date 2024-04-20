package com.nhnacademy.auth.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.nhnacademy.auth.dto.User;
import com.nhnacademy.auth.dto.JwtPayloadDto;
import com.nhnacademy.auth.exception.AccessTokenNotFoundException;
import com.nhnacademy.auth.exception.ExpiredJwtTokenException;
import com.nhnacademy.auth.exception.IpIsNotEqualsException;
import com.nhnacademy.auth.properties.JwtProperties;
import com.nhnacademy.auth.repository.AccessTokenRepository.IpAndBrowser;
import com.nhnacademy.auth.service.AccessTokenService;
import com.nhnacademy.auth.service.IpGeolationService;
import com.nhnacademy.auth.service.JwtTokenService;
import com.nhnacademy.auth.thread.UserAgentStore;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.util.Base64;
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

  /**
   * Jwt 을 발급하고, 발급된 토큰을 저장하는 메서드.
   * @param user : userId, userRole, userStatus
   * @return : accessToken
   */
  @Override
  public String issueAndSaveAccessToken(User user) {

    Long userId = user.getId();
    String ip = UserAgentStore.getUserIp();
    String browser = UserAgentStore.getUserBrowser();
    log.info("ip : {}, browser : {}", ip, browser);
    String accessToken = createJwt(userId, EXPIRED_TIME_MINUTE);
    accessTokenService.saveAccessToken(accessToken, ip, userId, browser);

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
  public String createJwt(Long userId, int expiredTime) {
    Claims claims = Jwts.claims();
    Date now = new Date();

    byte[] jwtSecret = jwtProperties.getSecret().getBytes();

    claims.put("userId", userId);
    log.debug("claims {} ", claims);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(DateUtils.addMinutes(now, expiredTime))
        .signWith(Keys.hmacShaKeyFor(jwtSecret))
        .compact();
  }

  @Override
  public String generateJwtFromMobile(User user)
      throws IOException, GeoIp2Exception {
    String ip = UserAgentStore.getUserIp();
    String mobileIpCountry = ipGeolationService.getContury(ip);
    UserAgentStore.setUserIp(mobileIpCountry);
    return issueAndSaveAccessToken(user);
  }

  @Override
  public String regenerateAccessToken(String nowIp, String browser, String legacyAccessToken)
      throws JsonProcessingException {

    IpAndBrowser ipAndBrowser = accessTokenService.getIpAndBrowser(legacyAccessToken).orElse(null);

    String encodeIp = DigestUtils.sha256Hex(nowIp);

    if (!accessTokenService.existsAccessToken(legacyAccessToken)) {
      throw new AccessTokenNotFoundException();
    }
    if (!(ipAndBrowser.getIp().equals(encodeIp) && ipAndBrowser.getBrowser().equals(browser))) {
      throw new IpIsNotEqualsException();
    }

    Long userId = Long.valueOf(getUserIdFromJwt(legacyAccessToken));

    String newAccessToken = createJwt(userId, EXPIRED_TIME_MINUTE);
    accessTokenService.updateAccessToken(legacyAccessToken, newAccessToken);
    return newAccessToken;
  }

  @Override
  public String getUserIdFromJwt(String token) throws JsonProcessingException {
    return parsing(token).getUserId();
  }

  public String getExpiredTimeFromJwt(String token) throws JsonProcessingException {
    return parsing(token).getExp();
  }

  private JwtPayloadDto parsing(String token) throws JsonProcessingException {
    String encodePayload = token.split("\\.")[1];
    String decode = new String(Base64.getDecoder().decode(encodePayload));
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(decode, JwtPayloadDto.class);
  }
}
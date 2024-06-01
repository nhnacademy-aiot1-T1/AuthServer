package com.nhnacademy.auth.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.auth.common.DateHolder;
import com.nhnacademy.auth.common.UserAgentStore;
import com.nhnacademy.auth.dto.domain.UserInfo;
import com.nhnacademy.auth.entity.TokenIssuanceInfo;
import com.nhnacademy.auth.exception.ParseException;
import com.nhnacademy.auth.exception.TokenNotReissuableException;
import com.nhnacademy.auth.exception.UserAgentMismatchException;
import com.nhnacademy.auth.properties.JwtProperties;
import com.nhnacademy.auth.repository.AccessTokenRepository;
import com.nhnacademy.auth.service.IpGeolocationService;
import com.nhnacademy.auth.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;


/**
 * jwt 토큰 생성 및 검증을 담당하는 클래스.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class JwtServiceImpl implements JwtService {

  private static final String KEY_USER_ID = "userId";
  private static final String KEY_NAME = "name";
  private static final String KEY_ROLE = "role";
  private static final String JWT_PAYLOAD_DELIMITER = "\\.";
  private static final int JWT_PAYLOAD_INDEX = 1;
  private static final int EXPIRED_TIME_MINUTE = 5;

  private final UserAgentStore userAgentStore;
  private final JwtProperties jwtProperties;
  private final AccessTokenRepository accessTokenRepository;
  private final IpGeolocationService ipGeolocationService;
  private final DateHolder dateHolder;
  private final ObjectMapper objectMapper;


  /**
   * jwt 토큰 발급.
   *
   * @param user : UserInfo 객체
   * @return : jwt
   */
  @Override
  public String issueJwt(UserInfo user) {
    Long userId = user.getId();
    String ip = userAgentStore.getUserIp();
    String browser = userAgentStore.getUserBrowser();
    log.info("ip : {}, browser : {}", ip, browser);
    String accessToken = generateJwt(user);
    saveJwt(userId, accessToken);
    return accessToken;
  }

  /**
   * 액세스을 토큰 DB 에서 삭제해 토큰 만료.
   *
   * @param accessToken : jwt
   */
  @Override
  public void expireToken(String accessToken) {
    if (!accessTokenRepository.existsById(accessToken)) {
      return;
    }
    accessTokenRepository.deleteById(accessToken);
  }

  /**
   * 토큰의 IP 와 브라우저 정보와 요청의 IP 와 브라우저 정보가 일치하는지 검증.
   *
   * @param tokenInfo : jwt
   */
  @Override
  public void validateLocationChanged(TokenIssuanceInfo tokenInfo) {
    final String requestIp = userAgentStore.getUserIp();
    final String requestBrowser = userAgentStore.getUserBrowser();
    final String tokenIp = tokenInfo.getIp();
    final String tokenBrowser = tokenInfo.getBrowser();

    if (!StringUtils.equals(tokenBrowser, requestBrowser)) {
      throw new UserAgentMismatchException();
    }
    final String requestDevice = userAgentStore.getUserDevice();

    if (StringUtils.equalsIgnoreCase(requestDevice, "mobile")) {
      String requestCountry = ipGeolocationService.getCountry(requestIp);
      String tokenCountry = ipGeolocationService.getCountry(tokenIp);
      if (!StringUtils.equalsIgnoreCase(requestCountry, tokenCountry)) {
        throw new UserAgentMismatchException();
      }
      return;
    }
    if (!StringUtils.equals(tokenIp, requestIp)) {
      throw new UserAgentMismatchException();
    }
  }


  @Override
  public boolean canReissue(String token) {
    return accessTokenRepository.existsById(token);
  }

  @Override
  public TokenIssuanceInfo getTokenIssuanceInfo(String token) {
    return accessTokenRepository.findById(token)
        .orElseThrow(TokenNotReissuableException::new);
  }

  /**
   * jwt 에서 사용자 아이디 추출.
   *
   * @param token : jwt
   * @return : 사용자 아이디
   */
  @Override
  public Long extractUserId(String token) {
    Base64.Decoder decoder = Base64.getDecoder();
    try {
      String payload = new String(
          decoder.decode(token.split(JWT_PAYLOAD_DELIMITER)[JWT_PAYLOAD_INDEX]));
      Map<String, String> returnMap = objectMapper.readValue(payload, new TypeReference<>() {
      });
      String stringUserId = returnMap.get(KEY_USER_ID);
      return Long.parseLong(stringUserId);
    } catch (JsonProcessingException | ArrayIndexOutOfBoundsException e) {
      throw new ParseException();
    }
  }


  private String generateJwt(UserInfo user) {
    Claims claims = Jwts.claims();

    claims.put(KEY_USER_ID, user.getId());
    claims.put(KEY_NAME, user.getName());
    claims.put(KEY_ROLE, user.getRole());
    log.debug("claims {} ", claims);

    byte[] secret = jwtProperties.getSecret().getBytes();

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(dateHolder.now())
        .setExpiration(DateUtils.addMinutes(dateHolder.now(), EXPIRED_TIME_MINUTE))
        .signWith(Keys.hmacShaKeyFor(secret))
        .compact();
  }

  /**
   * jwt 저장.
   *
   * @param userId      : 사용자 아이디
   * @param accessToken : jwt
   */
  private void saveJwt(Long userId, String accessToken) {
    String browser = userAgentStore.getUserBrowser();
    String ip = userAgentStore.getUserIp();
    log.debug("ip : {}, browser : {}", ip, browser);

    TokenIssuanceInfo tokenIssuanceInfoEntity = TokenIssuanceInfo.builder()
        .accessToken(accessToken)
        .userId(userId)
        .ip(ip)
        .browser(browser)
        .build();

    accessTokenRepository.save(tokenIssuanceInfoEntity);
  }

}



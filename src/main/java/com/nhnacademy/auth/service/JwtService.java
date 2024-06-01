package com.nhnacademy.auth.service;

import com.nhnacademy.auth.dto.domain.UserInfo;
import com.nhnacademy.auth.entity.TokenIssuanceInfo;

/**
 * JWT 서비스.
 */
public interface JwtService {

  String issueJwt(UserInfo user);

  void expireToken(String accessToken);

  void validateLocationChanged(TokenIssuanceInfo tokenInfo);

  boolean canReissue(String token);

  TokenIssuanceInfo getTokenIssuanceInfo(String token);

  Long extractUserId(String token);
}

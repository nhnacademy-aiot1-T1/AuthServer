package com.nhnacademy.auth.service;

import com.nhnacademy.auth.domain.AccessToken;

public interface AccessTokenService {

  boolean findAccessToken(String accessToken);

  AccessToken saveAccessToken(String accessToken, String ip, String userId, String userAgentBrowser);

  boolean updateAccessToken(String legacyAccessToken, String accessToken);

  void deleteAccessToken(String accessToken);
}

package com.nhnacademy.auth.service;

import com.nhnacademy.auth.domain.AccessToken;
import com.nhnacademy.auth.dto.RegenerateAccessTokenDto;

public interface AccessTokenService {

  boolean findAccessToken(String accessToken);

  AccessToken saveAccessToken(String accessToken, String ip);

  boolean updateAccessToken(String legacyAccessToken, String accessToken);

  void deleteAccessToken(String accessToken);
}

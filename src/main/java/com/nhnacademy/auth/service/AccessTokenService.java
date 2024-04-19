package com.nhnacademy.auth.service;

import com.nhnacademy.auth.domain.AccessToken;
import com.nhnacademy.auth.repository.AccessTokenRepository.IpAndBrowser;
import java.util.Optional;

public interface AccessTokenService {

  boolean findAccessToken(String accessToken);

  AccessToken saveAccessToken(String accessToken, String ip, String userId, String userAgentBrowser);

  boolean updateAccessToken(String legacyAccessToken, String accessToken);

  void deleteAccessToken(String accessToken);

  Optional<IpAndBrowser> getIpAndBrowser(String accessToken);
}

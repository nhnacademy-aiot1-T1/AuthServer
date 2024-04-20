package com.nhnacademy.auth.service;

import com.nhnacademy.auth.domain.AccessToken;
import com.nhnacademy.auth.repository.AccessTokenRepository.IpAndBrowser;
import java.util.Optional;

public interface AccessTokenService {

  boolean existsAccessToken(String accessToken);

  AccessToken saveAccessToken(String accessToken, String ip, Long userId, String userAgentBrowser);

  boolean updateAccessToken(String legacyAccessToken, String accessToken);

  void deleteAccessToken(String accessToken);

  Optional<IpAndBrowser> getIpAndBrowser(String accessToken);
}

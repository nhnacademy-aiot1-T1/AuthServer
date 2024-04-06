package com.nhnacademy.auth.service.impl;

import com.nhnacademy.auth.domain.AccessToken;
import com.nhnacademy.auth.repository.AccessTokenRepository;
import com.nhnacademy.auth.service.AccessTokenService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccessTokenServiceImpl implements AccessTokenService {

  private final AccessTokenRepository accessTokenRepository;


  @Override
  public boolean findAccessToken(String accessToken) {
    return accessTokenRepository.existsById(accessToken);
  }

  @Override
  public AccessToken saveAccessToken(String accessToken, String ip) {
    AccessToken newAccessToken = new AccessToken(accessToken, ip);
    return accessTokenRepository.save(newAccessToken);
  }

  @Override
  public boolean updateAccessToken(String legacyAccessToken, String accessToken) {
    Optional<AccessToken> value = accessTokenRepository.findById(legacyAccessToken);
    if (findAccessToken(legacyAccessToken)) {
      deleteAccessToken(legacyAccessToken);
      accessTokenRepository.flush();
      accessTokenRepository.save(new AccessToken(accessToken, value.get().getIp()));
      return true;
    }
    return false;
  }

  @Override
  public void deleteAccessToken(String accessToken) {
    accessTokenRepository.deleteById(accessToken);
  }
}

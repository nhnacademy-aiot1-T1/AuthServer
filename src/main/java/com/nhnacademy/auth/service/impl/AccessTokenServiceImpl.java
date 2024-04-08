package com.nhnacademy.auth.service.impl;

import com.nhnacademy.auth.domain.AccessToken;
import com.nhnacademy.auth.repository.AccessTokenRepository;
import com.nhnacademy.auth.service.AccessTokenService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * AccessToken을 mysql에 crud하는 class method는 4종.
 * <li>1. findAccessToken - return: boolean
 * <li>2. saveAccessToken - return: accessToken DTO
 * <li>3. updateAccessToken - return: boolean
 * <li>4. deleteAccessToken - return: void
 */
@RequiredArgsConstructor
@Service
public class AccessTokenServiceImpl implements AccessTokenService {

  private final AccessTokenRepository accessTokenRepository;


  @Override
  public boolean findAccessToken(String accessToken) {
    return accessTokenRepository.existsById(accessToken);
  }

  /**
   * param을 받아, mysql에 key, value 값으로 저장.
   *
   * @param accessToken : pk
   * @return : AccessToken DTO
   */
  @Override
  public AccessToken saveAccessToken(String accessToken, String ip, String userId) {
    AccessToken newAccessToken = new AccessToken(accessToken, ip, userId);
    return accessTokenRepository.save(newAccessToken);
  }

  /**
   * mysql db에 key값이 있는지 확인하고, 이에 따라 동작하는 method
   * <li> true : 기존에 있던 토큰을 지우고, flush를 한 뒤, 토큰을 저장.
   * <li> false : return false
   *
   * @return : true, false
   */
  @Override
  public boolean updateAccessToken(String legacyAccessToken, String accessToken) {
    Optional<AccessToken> value = accessTokenRepository.findById(legacyAccessToken);
    if (value.isEmpty()) {
      return false;
    }
    if (!findAccessToken(legacyAccessToken)) {
      return false;
    }
    deleteAccessToken(legacyAccessToken);
    accessTokenRepository.flush();
    accessTokenRepository.save(
        new AccessToken(accessToken, value.get().getIp(), value.get().getUserId()));
    return true;
  }

  @Override
  public void deleteAccessToken(String accessToken) {
    accessTokenRepository.deleteById(accessToken);
  }
}

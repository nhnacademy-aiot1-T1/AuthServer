package com.nhnacademy.auth.service.impl;

import com.nhnacademy.auth.dto.domain.OauthUserInfo;
import com.nhnacademy.auth.dto.domain.UserCredentials;
import com.nhnacademy.auth.dto.domain.UserInfo;
import com.nhnacademy.auth.entity.TokenIssuanceInfo;
import com.nhnacademy.auth.enums.OauthType;
import com.nhnacademy.auth.exception.PasswordNotMatchException;
import com.nhnacademy.auth.exception.TokenNotReissuableException;
import com.nhnacademy.auth.service.AccountService;
import com.nhnacademy.auth.service.AuthService;
import com.nhnacademy.auth.service.OauthService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 로그인, 로그아웃, 토큰 재발급을 담당하는 서비스 클래스.
 */
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final PasswordEncoder passwordEncoder;
  private final AccountService accountService;
  private final JwtServiceImpl jwtServiceImpl;
  private final Map<String, OauthService> oauthServiceMap;

  /**
   * 로그인에 성공했을 경우 : accessToken 발급.
   *
   * @param loginId  : 로그인 아이디
   * @param password : 비밀번호
   * @return : accessToken
   */
  @Override
  public String login(final String loginId, final String password) {
    UserCredentials user = accountService.getUserCredentialsByLoginId(loginId);
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new PasswordNotMatchException(loginId);
    }
    UserInfo userInfo = accountService.getUserInfoById(user.getId());
    return jwtServiceImpl.issueJwt(userInfo);
  }

  /**
   * oauth 로그인 성공 후 받은 authCode를 받아 accessToken 발급.
   *
   * @param oauthType : oauthType
   * @param authCode  : authCode
   * @return : accessToken
   */
  @Override
  public String oauthLogin(final String oauthType, final String authCode) {
    String oauthServiceName = OauthType.valueOf(oauthType).getBeanName();
    OauthService oauthService = oauthServiceMap.get(oauthServiceName);
    String accessToken = oauthService.requestAccessToken(authCode);
    OauthUserInfo oauthUserInfo = oauthService.requestOauthUserInfo(accessToken);
    UserInfo userInfo = accountService.getUserInfoByOauthId(oauthUserInfo.getOauthId());
    if (userInfo == null) {
      String oauthId = oauthUserInfo.getOauthId();
      String email = oauthUserInfo.getEmail();
      String name = oauthUserInfo.getName();
      userInfo = accountService.registerOauthUser(oauthType, oauthId, name, email);
    }
    return jwtServiceImpl.issueJwt(userInfo);
  }

  @Override
  public void logout(String accessToken) {
    jwtServiceImpl.expireToken(accessToken);
  }

  /**
   * 만료된 토큰을 받아 토큰을 발급 받은 IP와 브라우저를 비교해 같은 곳에서 재발급 요청이 오면 새로운 토큰을 발급.
   *
   * @param expiredToken : 만료된 토큰
   * @return : 재발급된 토큰
   */
  @Override
  public String reissueToken(String expiredToken) {
    if (!jwtServiceImpl.canReissue(expiredToken)) {
      throw new TokenNotReissuableException();
    }
    TokenIssuanceInfo tokenIssueInfo = jwtServiceImpl.getTokenIssuanceInfo(expiredToken);
    jwtServiceImpl.validateLocationChanged(tokenIssueInfo);
    Long userId = jwtServiceImpl.extractUserId(expiredToken);
    UserInfo user = accountService.getUserInfoById(userId);
    String reissueToken = jwtServiceImpl.issueJwt(user);
    jwtServiceImpl.expireToken(expiredToken);
    return reissueToken;
  }
}
package com.nhnacademy.auth.service;

import com.nhnacademy.auth.dto.domain.OauthUserInfo;

/**
 * Oauth 서비스.
 */
public interface OauthService {

  /**
   * Oauth 인증 코드로 엑세스 토큰을 요청.
   *
   * @param authCode 인증 코드
   * @return 엑세스 토큰
   */
  String requestAccessToken(String authCode);

  /**
   * Oauth 엑세스 토큰으로 사용자 정보를 요청.
   *
   * @param accessToken 엑세스 토큰
   * @return Oauth 사용자 정보
   */
  OauthUserInfo requestOauthUserInfo(String accessToken);

}

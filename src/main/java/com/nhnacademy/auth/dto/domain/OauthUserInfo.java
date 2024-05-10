package com.nhnacademy.auth.dto.domain;

/**
 * Oauth 사용자 정보.
 */
public interface OauthUserInfo {
  String getName();

  String getEmail();

  String getOauthId();
}

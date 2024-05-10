package com.nhnacademy.auth.service;

import com.nhnacademy.auth.dto.domain.UserCredentials;
import com.nhnacademy.auth.dto.domain.UserInfo;

/**
 *  User Account Service.
 */
public interface AccountService {

  UserCredentials getUserCredentialsByLoginId(String loginId);

  UserInfo getUserInfoById(Long userId);

  UserInfo getUserInfoByOauthId(String oauthId);

  UserInfo registerOauthUser(String oauthType, String oauthId, String name, String email);
}

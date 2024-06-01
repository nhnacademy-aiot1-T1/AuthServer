package com.nhnacademy.auth.service.impl;

import com.nhnacademy.auth.adaptor.AccountAdapter;
import com.nhnacademy.auth.dto.domain.UserCredentials;
import com.nhnacademy.auth.dto.domain.UserInfo;
import com.nhnacademy.auth.exception.UserNotFoundException;
import com.nhnacademy.auth.service.AccountService;
import com.nhnacademy.auth.service.dto.request.OauthUserRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * AccountService 구현체.
 */
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

  private final AccountAdapter accountAdapter;

  @Override
  public UserCredentials getUserCredentialsByLoginId(String loginId) {
    return accountAdapter.getUserCredentialsByLoginId(loginId)
        .dataOrElseThrow(UserNotFoundException::new);
  }

  @Override
  public UserInfo getUserInfoById(Long userId) {
    return accountAdapter.getUserInfoById(userId)
        .dataOrElseThrow(UserNotFoundException::new);
  }

  @Override
  public UserInfo getUserInfoByOauthId(String oauthId) {
    return accountAdapter.getUserInfoByOauthId(oauthId).getData();
  }

  @Override
  public UserInfo registerOauthUser(String oauthType, String oauthId, String name, String email) {
    OauthUserRegisterRequest request = OauthUserRegisterRequest.builder()
                                                               .oauthType(oauthType)
                                                               .oauthId(oauthId)
                                                               .name(name)
                                                               .email(email)
                                                               .build();
    return accountAdapter.registerOauthUser(request).getData();
  }
}

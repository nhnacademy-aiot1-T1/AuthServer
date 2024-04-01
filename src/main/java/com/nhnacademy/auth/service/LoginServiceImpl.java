package com.nhnacademy.auth.service;

import com.nhnacademy.auth.adapter.AccountAdapter;
import com.nhnacademy.auth.dto.LoginInfo;
import com.nhnacademy.auth.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Account api와 통신하여 Login 관련 정보를 받아오는 서비스 입니다
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
  private final AccountAdapter accountAdapter;
  private final BCryptPasswordEncoder passwordEncoder;

  @Override
  public boolean match(LoginInfo loginRequest) {
    LoginInfo loginInfo = accountAdapter.getAccountInfo(loginRequest.getId()).orElseThrow();
    return passwordEncoder.matches(loginRequest.getPassword(), loginInfo.getPassword());
  }

  @Override
  public User getUser(String userId) {
    return accountAdapter.getUserInfo(userId);
  }

}

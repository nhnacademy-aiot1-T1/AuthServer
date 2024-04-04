package com.nhnacademy.auth.service.impl;

import com.nhnacademy.auth.adaptor.AccountAdapter;
import com.nhnacademy.auth.dto.LoginInfo;
import com.nhnacademy.auth.dto.User;
import com.nhnacademy.auth.exception.UserIdNotFoundException;
import com.nhnacademy.auth.service.LoginService;
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
    LoginInfo loginInfo = accountAdapter.getAccountInfo(loginRequest.getId())
        .dataOrElseThrow(RuntimeException::new);
    if (loginInfo == null) {
      throw new UserIdNotFoundException();
    }
    return passwordEncoder.matches(loginRequest.getPassword(), loginInfo.getPassword());
  }

  @Override
  public User getUser(String userId) {
    User user = accountAdapter.getUserInfo(userId).dataOrElseThrow(RuntimeException::new);
    if (user == null) {
      throw new UserIdNotFoundException();
    }
    return user;
  }

}

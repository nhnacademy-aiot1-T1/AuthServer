package com.nhnacademy.auth.service.impl;

import com.nhnacademy.auth.adaptor.AccountAdapter;
import com.nhnacademy.auth.dto.LoginInfo;
import com.nhnacademy.auth.dto.User;
import com.nhnacademy.auth.exception.PasswordNotMatchException;
import com.nhnacademy.auth.exception.UserIdNotFoundException;
import com.nhnacademy.auth.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Account api와 통신하여 Login 관련 정보를 받아오는 서비스 입니다
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

  private final AccountAdapter accountAdapter;
  private final BCryptPasswordEncoder passwordEncoder;

  @Override
  public void match(LoginInfo requestInfo, LoginInfo userInfo) {
    if(!passwordEncoder.matches(requestInfo.getPassword(), userInfo.getPassword())){
      throw new PasswordNotMatchException(requestInfo.getLoginId());
    }
  }

  public LoginInfo getAccountInfo(String loginId) {
    return accountAdapter.getAccountInfo(loginId).dataOrElseThrow(UserIdNotFoundException::new);
  }

  @Override
  public User getUser(Long userId) {
    User user = accountAdapter.getUserInfo(userId).dataOrElseThrow(RuntimeException::new);
    log.debug("user : {}", user);
    if (user == null) {
      throw new UserIdNotFoundException();
    }
    return user;
  }

}

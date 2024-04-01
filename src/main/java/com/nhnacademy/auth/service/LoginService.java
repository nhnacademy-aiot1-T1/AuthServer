package com.nhnacademy.auth.service;


import com.nhnacademy.auth.dto.LoginInfo;
import com.nhnacademy.auth.dto.User;

public interface LoginService {
  boolean match(LoginInfo loginRequest);

  User getUser(String userId);

}

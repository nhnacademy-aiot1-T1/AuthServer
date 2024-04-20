package com.nhnacademy.auth.service;

import com.nhnacademy.auth.dto.LoginInfo;
import com.nhnacademy.auth.dto.User;

public interface LoginService {
    void match(LoginInfo loginRequest, LoginInfo userInfo);
    LoginInfo getAccountInfo(String loginId);
    User getUser(Long userId);
}

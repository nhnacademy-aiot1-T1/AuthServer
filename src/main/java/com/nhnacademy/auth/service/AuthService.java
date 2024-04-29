package com.nhnacademy.auth.service;

/**
 * 인증 서비스.
 */
public interface AuthService {

  String login(String username, String password);

  String oauthLogin(String oauthToken);

  void logout(String token);

  String reissueToken(String token);



}

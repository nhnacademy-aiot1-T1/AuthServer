package com.nhnacademy.auth.common;

/**
 * UserAgent 정보를 저장하는 인터페이스.
 */
public interface UserAgentStore {

  void setUserBrowser(String userAgent);

  String getUserBrowser();

  void setUserIp(String ip);

  String getUserIp();

  void clear();

}

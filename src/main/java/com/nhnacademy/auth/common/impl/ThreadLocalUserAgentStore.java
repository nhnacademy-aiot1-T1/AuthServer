package com.nhnacademy.auth.common.impl;

import com.nhnacademy.auth.common.UserAgentStore;
import org.springframework.stereotype.Component;

/**
 * ThreadLocal을 이용한 UserAgentStore 구현 클래스.
 */
@Component
public class ThreadLocalUserAgentStore implements UserAgentStore {

  private static final ThreadLocal<String> threadLocalUserBrowser = new ThreadLocal<>();
  private static final ThreadLocal<String> threadLocalUserIp = new ThreadLocal<>();

  @Override
  public void setUserBrowser(String userAgent) {
    threadLocalUserBrowser.set(userAgent);
  }


  @Override
  public String getUserBrowser() {
    return threadLocalUserBrowser.get();
  }

  @Override
  public void setUserIp(String ip) {
    threadLocalUserIp.set(ip);
  }

  @Override
  public String getUserIp() {
    return threadLocalUserIp.get();
  }

  @Override
  public void clear() {
    threadLocalUserBrowser.remove();
    threadLocalUserIp.remove();
  }
}

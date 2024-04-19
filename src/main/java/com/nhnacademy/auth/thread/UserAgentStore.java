package com.nhnacademy.auth.thread;

public class UserAgentStore {

  private static final ThreadLocal<String> currentUserBrowser = new ThreadLocal<>();
  private static final ThreadLocal<String> currentUserIp = new ThreadLocal<>();

  private UserAgentStore() {
  }

  public static void setUserBrowser(String userAgent) {
    currentUserBrowser.set(userAgent);
  }
  public static String getUserBrowser() {
    return currentUserBrowser.get();
  }
  public static void setUserIp(String ip) {
    currentUserIp.set(ip);
  }
  public static String getUserIp() {
    return currentUserIp.get();
  }
  public static void clear(){
    currentUserBrowser.remove();
    currentUserIp.remove();
  }
}

package com.nhnacademy.auth.interceptor;

import com.nhnacademy.auth.exception.BadRequestException;
import com.nhnacademy.auth.thread.UserAgentStore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class UserAgentInterceptor implements HandlerInterceptor {

  private static final String USER_BROWSER_HEADER = "X-USER-BROWSER";
  private static final String USER_IP_HEADER = "X-USER-IP";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) {

    String userBrowser = request.getHeader(USER_BROWSER_HEADER);
    String userIp = request.getHeader(USER_IP_HEADER);

    if (!StringUtils.hasText(userBrowser) || !StringUtils.hasText(userIp)) {
      throw new BadRequestException("Invalid User Agent");
    }

    UserAgentStore.setUserIp(userIp);
    UserAgentStore.setUserBrowser(userBrowser);

    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) {
    UserAgentStore.clear();
  }
}

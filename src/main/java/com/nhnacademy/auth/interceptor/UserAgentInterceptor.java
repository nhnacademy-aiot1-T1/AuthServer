package com.nhnacademy.auth.interceptor;

import com.nhnacademy.auth.common.UserAgentStore;
import com.nhnacademy.auth.exception.InvalidUserAgentException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * User-Agent 정보를 저장하는 인터셉터.
 */
@Slf4j
@RequiredArgsConstructor
public class UserAgentInterceptor implements HandlerInterceptor {

  private final UserAgentStore userAgentStore;
  private static final String USER_BROWSER_HEADER = "X-USER-BROWSER";
  private static final String USER_IP_HEADER = "X-USER-IP";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler) {

    String userBrowser = request.getHeader(USER_BROWSER_HEADER);
    String userIp = request.getHeader(USER_IP_HEADER);

    if (!StringUtils.hasText(userBrowser) || !StringUtils.hasText(userIp)) {
      throw new InvalidUserAgentException("Invalid User-Agent");
    }

    userAgentStore.setUserIp(userIp);
    userAgentStore.setUserBrowser(userBrowser);

    return true;
  }


  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) {
    userAgentStore.clear();
  }
}

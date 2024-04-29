package com.nhnacademy.auth.config;

import com.nhnacademy.auth.interceptor.UserAgentInterceptor;
import com.nhnacademy.auth.common.UserAgentStore;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 설정 클래스.
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

  private final UserAgentStore userAgentStore;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(userAgentInterceptor())
        .addPathPatterns("/api/auth/**");
  }

  @Bean
  public UserAgentInterceptor userAgentInterceptor() {
    return new UserAgentInterceptor(userAgentStore);
  }

}

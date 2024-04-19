package com.nhnacademy.auth.config;

import com.nhnacademy.auth.interceptor.UserAgentInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new UserAgentInterceptor())
        .addPathPatterns("/api/auth/login/**");
  }
}

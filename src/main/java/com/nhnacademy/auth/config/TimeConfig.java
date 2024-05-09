package com.nhnacademy.auth.config;

import com.nhnacademy.auth.common.DateHolder;
import java.util.Date;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 시간 관련 설정 클래스.
 */
@Configuration
public class TimeConfig {

  @Bean
  public DateHolder systemDateHolder() {
    return Date::new;
  }

}

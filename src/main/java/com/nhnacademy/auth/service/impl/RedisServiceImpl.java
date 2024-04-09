package com.nhnacademy.auth.service.impl;

import com.nhnacademy.auth.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * Redis template의 param = key (String), value (String)
 */
@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

  private final RedisTemplate<String, String> redisTemplate;

  /**
   * TTL 기능이 있음. timeDuration으로 설정 가능. 단위: sec
   *
   * @param accessToken
   * @param userId
   * @param timeDuration : sec
   */
  @Override
  public void save(String accessToken, String userId, Duration timeDuration) {
    redisTemplate.opsForValue().set(accessToken, userId, timeDuration);
  }

  @Override
  public String findUserIdByAccessTokenToken(String accessToken) {
    return redisTemplate.opsForValue().get(accessToken);
  }

  @Override
  public Boolean haveThisKey(String accessToken) {
    return redisTemplate.hasKey(accessToken);
  }
}

package com.nhnacademy.auth.service.impl;

import com.nhnacademy.auth.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisServiceImpl implements RedisService {
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(String refreshToken, String userId, Duration timeDuration) {
        redisTemplate.opsForValue().set(refreshToken, userId, timeDuration);
    }

    @Override
    public String findRefreshTokenByUserId(String refreshToken) {
        return redisTemplate.opsForValue().get(refreshToken);
    }

    @Override
    public Boolean haveThisKey(String refreshToken) {
        return redisTemplate.hasKey(refreshToken);
    }
}

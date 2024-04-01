package com.nhnacademy.auth.provider;

import com.nhnacademy.auth.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void save(String userId, String refreshToken, Duration timeDuration) {
        redisTemplate.opsForValue().set(refreshToken, userId, timeDuration);
    }

    public String findByRefreshTokenToUserId(String refreshToken) {
        return redisTemplate.opsForValue().get(refreshToken);
    }

    public Boolean haveThisKey(String refreshToken) {
        return redisTemplate.hasKey(refreshToken);
    }

}

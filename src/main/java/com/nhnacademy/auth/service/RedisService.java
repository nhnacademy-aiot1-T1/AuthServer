package com.nhnacademy.auth.service;

import java.time.Duration;

public interface RedisService {
    void save(String refreshToken, String userId, Duration timeDuration);

    String findRefreshTokenByUserId(String refreshToken);

    Boolean haveThisKey(String refreshToken);

}

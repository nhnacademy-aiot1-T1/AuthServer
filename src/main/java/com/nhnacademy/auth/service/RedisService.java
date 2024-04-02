package com.nhnacademy.auth.service;

import java.time.Duration;

public interface RedisService {
    void save(String refreshToken, String userId, Duration timeDuration);

    String findUserIdByRefreshToken(String refreshToken);

    Boolean haveThisKey(String refreshToken);

}

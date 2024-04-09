package com.nhnacademy.auth.service;

import java.time.Duration;

public interface RedisService {
    void save(String accessToken, String userId, Duration timeDuration);

    String findUserIdByAccessTokenToken(String accessToken);

    Boolean haveThisKey(String accessToken);
}

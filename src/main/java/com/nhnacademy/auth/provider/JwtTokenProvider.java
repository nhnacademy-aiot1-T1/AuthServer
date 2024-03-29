package com.nhnacademy.auth.provider;

import com.nhnacademy.auth.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final int EXPIRED_TIME_MINUTE = 5;
    private static final int REFRESH_TOKEN_EXPIRED_TIME_WEEK = 1;

    private final JwtProperties jwtProperties;

    private final RedisTemplate<String, String> redisTemplate;
    private final ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

    public String generateAccessToken(String userId) {
        return createJwtToken(userId, EXPIRED_TIME_MINUTE);
    }

    public String generateRefreshToken(String userId) {
        String refreshToken = createJwtToken(userId, REFRESH_TOKEN_EXPIRED_TIME_WEEK);
        valueOperations.set(refreshToken, userId);
        return refreshToken;
    }

    private String createJwtToken (String userId, int expiredTime) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);

        Date now = new Date();


        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(DateUtils.addMinutes(now, expiredTime))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .compact();
    }

    public String regenerateAccessToken(String refreshToken) {
        if (checkRefreshToken(refreshToken)) {
            String getUserIdFromRedis = valueOperations.get(refreshToken);
            return generateAccessToken(getUserIdFromRedis);
        }
        return null;
    }

    private boolean checkRefreshToken(String refreshToken) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(refreshToken));
    }
}

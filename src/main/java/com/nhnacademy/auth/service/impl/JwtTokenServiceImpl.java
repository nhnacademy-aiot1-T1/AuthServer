package com.nhnacademy.auth.service.impl;

import com.nhnacademy.auth.exception.RefreshTokenNotFoundException;
import com.nhnacademy.auth.properties.JwtProperties;
import com.nhnacademy.auth.service.JwtTokenService;
import com.nhnacademy.auth.service.RedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

/**
 * UserId를 받아, JwtToken을 발급하는 Service layer
 */
@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    private final JwtProperties jwtProperties;

    private final RedisService redisService;

    private static final int EXPIRED_TIME_MINUTE = 5;
    private static final int REFRESH_TOKEN_EXPIRED_TIME_WEEK = 1;


    @Override
    public String generateAccessToken(String userId) {
        return createJwtToken(userId, EXPIRED_TIME_MINUTE);
    }

    /**
     * refresh token이 발급 된다면, 7일 후 자동적으로 삭제되게 TTL 설정.
     * @param userId
     * @return refresh token
     */
    @Override
    public String generateRefreshToken(String userId) {
        String refreshToken = createJwtToken(userId, REFRESH_TOKEN_EXPIRED_TIME_WEEK);
        redisService.save(refreshToken, userId, Duration.ofDays(7));
        return refreshToken;
    }

    /**
     * 실질적인 발급 로직.
     * @param userId : token 발급 대상.
     * @param expiredTime : 만료 시간. 토큰의 종류에 따라 만료 시간이 다름.
     * @return string : JwtToken
     */
    @Override
    public String createJwtToken(String userId, int expiredTime) {
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

    /**
     * redis에 refreshToken이 있는지 찾은 후, 유무에 따라 return이 달라진다.
     * @param refreshToken : 검색 대상
     * @exception RefreshTokenNotFoundException(String) : refresh이 redis에서 찾을 수 없을 경우.
     * @return accessToken
     */
    @Override
    public String regenerateAccessToken(String refreshToken) {
        if (checkRefreshToken(refreshToken)) {
            String getUserIdFromRedis = redisService.findUserIdByRefreshToken(refreshToken);
            return generateAccessToken(getUserIdFromRedis);
        }
        throw new RefreshTokenNotFoundException(refreshToken);
    }

    @Override
    public boolean checkRefreshToken(String refreshToken) {
        return redisService.haveThisKey(refreshToken);
    }
}
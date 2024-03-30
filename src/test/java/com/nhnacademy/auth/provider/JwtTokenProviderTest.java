package com.nhnacademy.auth.provider;

import com.nhnacademy.auth.properties.JwtProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private String userId;

    @BeforeEach
    void setUp() {
        userId = "testUser";
    }

    @Test
    void generateAccessToken() {
        String accessToken = jwtTokenProvider.generateAccessToken(userId);

        assertNotNull(accessToken);
        assertFalse(accessToken.isEmpty());
    }

    @Test
    void generateRefreshToken() {
        String refreshToken = jwtTokenProvider.generateRefreshToken(userId);

        assertNotNull(refreshToken);
        assertFalse(refreshToken.isEmpty());
    }

    @Test
    void regenerateAccessToken() {
        String accessToken = jwtTokenProvider.regenerateAccessToken(userId);

        assertNotNull(accessToken);
        assertFalse(accessToken.isEmpty());
    }
}
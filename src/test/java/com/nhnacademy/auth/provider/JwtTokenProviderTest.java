package com.nhnacademy.auth.provider;

import com.nhnacademy.auth.properties.JwtProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private String userId;

    private String refreshToken;

    @BeforeEach
    void setUp() {
        userId = "testUser";
        refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJ0ZXN0VXNlciIsImlhdCI6MTcxMTk0ODI3NiwiZXhwIjoxNzExOTQ4MzM2fQ.e0N5CYQshDFK8tv52P8VviCtJEuPfnlsgdeF6Q6bazk";

    }

    @Test
    @Order(1)
    void generateAccessToken() {
        String accessToken = jwtTokenProvider.generateAccessToken(userId);

        assertNotNull(accessToken);
        assertFalse(accessToken.isEmpty());
    }
    @Test
    @Order(3)
    void regenerateAccessToken() {
        String accessToken = jwtTokenProvider.regenerateAccessToken(refreshToken);

        assertNotNull(accessToken);
        assertFalse(accessToken.isEmpty());
    }

    @Test
    @Order(2)
    void generateRefreshToken() {
        String refreshToken = jwtTokenProvider.generateRefreshToken(userId);

        assertNotNull(refreshToken);
        assertFalse(refreshToken.isEmpty());
    }
}
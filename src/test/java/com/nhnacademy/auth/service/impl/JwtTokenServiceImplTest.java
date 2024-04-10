package com.nhnacademy.auth.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.nhnacademy.auth.dto.User;
import com.nhnacademy.auth.dto.User.Role;
import com.nhnacademy.auth.dto.User.Status;
import com.nhnacademy.auth.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class JwtTokenServiceImplTest {

  @Autowired
  private JwtTokenServiceImpl jwtTokenService;

  private String id;
  private String ip;
  private String legacyAccessToken;
  private String browser;

  @BeforeEach
  void setUp() {
    id = "id";
    ip = "112.216.11.34";
    legacyAccessToken = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJpZCIsInVzZXJSb2xlIjoiQUNUSVZFIiwidXNlcklwIjoiMjc2ZGYyMjE5NzdkNjBlZTA2NDU0ZDkwNWE3OWFmNmU0YmE4YzdlOTk4OWIxYWJkYzY2MzU0NTcyMmY0YjkxZSIsImlhdCI6MTcxMjMyMjAzMCwiZXhwIjoxNzEyMzI1MDMwfQ.Zey_LjHnTTYmwNtIvC0zv508iPFTxyC3qW646v71IVU";
    browser = "Mozilla";
  }

  @Test
  @DisplayName("generate access token in pc")
  @Order(1)
  void generateAccessToken() {
    User user = new User(id, browser, Status.ACTIVE, Role.USER);
    String accessToken = jwtTokenService.generateAccessToken(user, ip, browser);
    assertNotNull(accessToken);
  }

  @Test
  @DisplayName("generate access token in mobile")
  @Order(2)
  void generateJwtTokenFromMobile() throws IOException, GeoIp2Exception {
    User user = new User(id, browser, Status.ACTIVE, Role.USER);
    String generateJwtTokenFromMobile = jwtTokenService.generateJwtTokenFromMobile(user, ip, browser);
    log.warn(generateJwtTokenFromMobile);
    assertNotNull(generateJwtTokenFromMobile);
  }

  @Test
  @DisplayName("regenerate access token")
  @Order(3)
  void regenerateAccessToken() throws JsonProcessingException {
    String regenerateAccessToken = jwtTokenService.regenerateAccessToken("112.216.11.34",
        legacyAccessToken);
    log.warn(regenerateAccessToken);
    assertNotNull(regenerateAccessToken);
  }
}
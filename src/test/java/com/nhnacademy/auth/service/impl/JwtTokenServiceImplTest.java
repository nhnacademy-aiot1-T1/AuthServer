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

  @BeforeEach
  void setUp() {
    id = "id";
    ip = "112.216.11.34";
    legacyAccessToken = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJpZCIsInVzZXJSb2xlIjoiQUNUSVZFIiwidXNlcklwIjoiMjc2ZGYyMjE5NzdkNjBlZTA2NDU0ZDkwNWE3OWFmNmU0YmE4YzdlOTk4OWIxYWJkYzY2MzU0NTcyMmY0YjkxZSIsImlhdCI6MTcxMjMyMjAzMCwiZXhwIjoxNzEyMzI1MDMwfQ.Zey_LjHnTTYmwNtIvC0zv508iPFTxyC3qW646v71IVU";
  }

  @Test
  void generateAccessToken() {
    User user = new User(id, Status.USER, Role.ACTIVE);
    String accessToken = jwtTokenService.generateAccessToken(user, ip);
    assertNotNull(accessToken);
  }

  @Test
  void generateJwtTokenFromMobile() throws IOException, GeoIp2Exception {
    User user = new User(id, Status.USER, Role.ACTIVE);
    String generateJwtTokenFromMobile = jwtTokenService.generateJwtTokenFromMobile(user, ip);
    log.warn(generateJwtTokenFromMobile);
    assertNotNull(generateJwtTokenFromMobile);
  }

  @Test
  void regenerateAccessToken() throws JsonProcessingException {
    String regenerateAccessToken = jwtTokenService.regenerateAccessToken("112.216.11.34", legacyAccessToken);
    log.warn(regenerateAccessToken);
    assertNotNull(regenerateAccessToken);
  }
}
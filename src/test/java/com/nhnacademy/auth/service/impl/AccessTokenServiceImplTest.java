package com.nhnacademy.auth.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.nhnacademy.auth.domain.AccessToken;
import com.nhnacademy.auth.service.AccessTokenService;
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
class AccessTokenServiceImplTest {

  @Autowired
  private AccessTokenService accessTokenService;

  private String accessToken;
  private String newAccessToken;
  private String ip;
  private String clientId;
  private String browser;

  @BeforeEach
  void setUp() {
    accessToken = "accessToken";
    newAccessToken = "newAccessToken";
    ip = "ip";
    clientId = "clientId";
    browser = "chrome";
  }

  @Test
  @Order(2)
  @DisplayName("access token find in mysql")
  void findAccessToken() {
    assertNotNull(accessTokenService.findAccessToken(accessToken));
  }

  @Test
  @DisplayName("access token save in mysql")
  @Order(1)
  void saveAccessToken() {
    AccessToken accessToken1 = accessTokenService.saveAccessToken(accessToken, ip, clientId, browser);
    assertNotNull(accessToken1);
  }

  @Test
  @DisplayName("access token update in mysql")
  @Order(3)
  void updateAccessToken() {
    accessTokenService.updateAccessToken(accessToken, newAccessToken);
    assertNotNull(accessTokenService.findAccessToken(accessToken));
  }

  @Test
  @DisplayName("access token delete in mysql")
  @Order(4)
  void deleteAccessToken() {
    accessTokenService.deleteAccessToken(newAccessToken);
    assertFalse(accessTokenService.findAccessToken(accessToken));
  }
}
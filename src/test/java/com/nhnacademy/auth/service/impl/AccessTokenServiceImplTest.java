package com.nhnacademy.auth.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.nhnacademy.auth.domain.AccessToken;
import com.nhnacademy.auth.service.AccessTokenService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

@Slf4j
@SpringBootTest
class AccessTokenServiceImplTest {

  @Autowired
  private AccessTokenService accessTokenService;

  private String accessToken;
  private String newAccessToken;
  private String ip;

  @BeforeEach
  void setUp() {
    accessToken = "accessToken";
    newAccessToken = "newAccessToken";
    ip = "ip";
  }

  @Test
  @Order(2)
  void findAccessToken() {
    assertNotNull(accessTokenService.findAccessToken(accessToken));
  }

  @Test
  @Order(1)
  void saveAccessToken() {
    AccessToken accessToken1 = accessTokenService.saveAccessToken(accessToken, ip);
    assertNotNull(accessToken1);
  }

  @Test
  @Order(3)
  void updateAccessToken() {
    accessTokenService.updateAccessToken(accessToken, newAccessToken);
    assertNotNull(accessTokenService.findAccessToken(accessToken));
  }

  @Test
  @Order(4)
  void deleteAccessToken() {
    accessTokenService.deleteAccessToken(newAccessToken);
  }
}
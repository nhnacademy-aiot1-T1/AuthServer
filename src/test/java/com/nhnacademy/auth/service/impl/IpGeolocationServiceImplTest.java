package com.nhnacademy.auth.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class IpGeolocationServiceImplTest {

  @Autowired
  private IpGeolocationServiceImpl ipGeolocationService;

  private String ip;

  @BeforeEach
  void setUp() throws Exception {
    ip = "112.216.11.34";
  }

  @Test
  @DisplayName("get contury in mobile ip")
  void getContury() throws IOException, GeoIp2Exception {
    String i1p = ipGeolocationService.getCountry(ip);
    log.info(":{}", i1p);
    assertNotNull(ipGeolocationService.getCountry(ip));
  }
}
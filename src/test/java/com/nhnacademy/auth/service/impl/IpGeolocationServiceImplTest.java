package com.nhnacademy.auth.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.maxmind.geoip2.DatabaseReader;
import com.nhnacademy.auth.base.ServiceTest;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
class IpGeolocationServiceImplTest extends ServiceTest {

  private IpGeolocationServiceImpl ipGeolocationService;

  private String ip;

  @BeforeEach
  void setUp() throws Exception {

    ip = "112.216.11.34";
  }

  @Test
  @DisplayName("get contury in mobile ip")
  void getContury() throws IOException {
    BufferedInputStream inputStream = new BufferedInputStream( this.getClass().getClassLoader().getResourceAsStream("GeoLite2-Country.mmdb") );
    ipGeolocationService = new IpGeolocationServiceImpl(new DatabaseReader.Builder(inputStream).build());
    String countryCode = ipGeolocationService.getCountry(ip);
    assertNotNull(ipGeolocationService.getCountry(ip));
  }

  @Test
  @DisplayName("invalid ip")
  void invalidIp() throws IOException {
    String invalidIp = "111.489498.54.65481";
    InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("GeoLite2-Country.mmdb");
    BufferedInputStream bufferedInputStream = new BufferedInputStream( inputStream );
    ipGeolocationService = new IpGeolocationServiceImpl(new DatabaseReader.Builder(bufferedInputStream).build());
    assertThrows(Exception.class, () -> ipGeolocationService.getCountry(invalidIp));
  }
}
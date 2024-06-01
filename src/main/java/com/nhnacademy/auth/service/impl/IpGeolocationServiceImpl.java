package com.nhnacademy.auth.service.impl;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.nhnacademy.auth.service.IpGeolocationService;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * mobile일 경우, 국가가 어디인지 찾기 위한 service class.
 * <p>
 * method is
 * <li> getContury(String) : ip를 바탕으로 contury를 찾음.
 */
@Service
@RequiredArgsConstructor
public class IpGeolocationServiceImpl implements IpGeolocationService {

  private final DatabaseReader databaseReader;

  @Override
  // FIXME 사용자 정의 예외로 변경
  public String getCountry(String ip)  {
    InetAddress inetAddress;
    try {
      inetAddress = InetAddress.getByName(ip);
    } catch (UnknownHostException e) {

      throw new RuntimeException("UnknownHostException", e);
    }
    try {
      return databaseReader.country(inetAddress).getCountry().getIsoCode();
    } catch (IOException e) {
      throw new RuntimeException("IOException", e);
    } catch (GeoIp2Exception e) {
      throw new RuntimeException("GeoIp2Exception", e);
    }
  }
}

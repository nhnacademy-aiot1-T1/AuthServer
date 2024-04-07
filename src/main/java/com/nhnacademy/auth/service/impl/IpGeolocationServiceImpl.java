package com.nhnacademy.auth.service.impl;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.nhnacademy.auth.service.IpGeolationService;
import java.io.IOException;
import java.net.InetAddress;
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
public class IpGeolocationServiceImpl implements IpGeolationService {

  private final DatabaseReader databaseReader;

  @Override
  public String getContury(String ip) throws IOException, GeoIp2Exception {
    InetAddress inetAddress = InetAddress.getByName(ip);
    String conturyIp = databaseReader.country(inetAddress).getCountry().getIsoCode();
    System.out.printf("%s{}", conturyIp);
    return conturyIp;
  }
}

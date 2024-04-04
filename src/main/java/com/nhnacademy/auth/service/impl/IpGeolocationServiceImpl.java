package com.nhnacademy.auth.service.impl;

import com.nhnacademy.auth.properties.IpGeoProperties;
import com.nhnacademy.auth.service.IpGeolationService;
import io.ipgeolocation.api.Geolocation;
import io.ipgeolocation.api.GeolocationParams;
import io.ipgeolocation.api.IPGeolocationAPI;
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

  private final IpGeoProperties ipGeoProperties;

  @Override
  public String getContury(String ip) {

    GeolocationParams geolocationParams = GeolocationParams.builder()
        .withIPAddress(ip)
        .withFields("geo, time_zone, currency")
        .includeSecurity()
        .build();

    Geolocation geolocation = new IPGeolocationAPI(ipGeoProperties.getSecret()).getGeolocation(
        geolocationParams);

    if (geolocation.getStateCode().equals("200")) {
      return geolocation.getCountryName();
    }
    return null;
  }
}

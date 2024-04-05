package com.nhnacademy.auth.service;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import java.io.IOException;

public interface IpGeolationService {

  String getContury(String ip) throws IOException, GeoIp2Exception;

}

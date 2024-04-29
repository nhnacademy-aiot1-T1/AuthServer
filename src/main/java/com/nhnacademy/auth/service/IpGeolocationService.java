package com.nhnacademy.auth.service;


/**
 * IP Geolocation 서비스.
 */
public interface IpGeolocationService {

  String getCountry(String ip);

}

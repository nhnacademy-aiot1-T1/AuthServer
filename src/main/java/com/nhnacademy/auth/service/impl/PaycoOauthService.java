package com.nhnacademy.auth.service.impl;

import com.nhnacademy.auth.dto.PaycoUserInfo;
import com.nhnacademy.auth.dto.domain.OauthUserInfo;
import com.nhnacademy.auth.exception.OauthServerException;
import com.nhnacademy.auth.properties.PaycoOauthProperties;
import com.nhnacademy.auth.service.OauthService;
import com.nhnacademy.auth.service.dto.PaycoAccessTokenResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Payco Oauth 서비스.
 */
@Service
@RequiredArgsConstructor
public class PaycoOauthService implements OauthService {

  public static final String PARAM_GRANT_TYPE = "grant_type";
  public static final String PARAM_CLIENT_ID = "client_id";
  public static final String PARAM_CLIENT_SECRET = "client_secret";
  public static final String PARAM_CODE = "code";
  public static final String PARAM_ACCESS_TOKEN = "access_token";
  public static final String GRANT_TYPE_VALUE = "authorization_code";

  private final PaycoOauthProperties paycoOauthProperties;
  private final RestTemplate restTemplate;

  /**
   * Payco 인증 코드로 엑세스 토큰을 요청.
   *
   * @param authCode 인증 코드
   * @return 엑세스 토큰
   */
  @Override
  public String requestAccessToken(String authCode) {
    URI uri = UriComponentsBuilder
        .fromUriString(paycoOauthProperties.getTokenUrl())
        .queryParam(PARAM_GRANT_TYPE, GRANT_TYPE_VALUE)
        .queryParam(PARAM_CLIENT_ID, paycoOauthProperties.getClientId())
        .queryParam(PARAM_CLIENT_SECRET, paycoOauthProperties.getClientSecret())
        .queryParam(PARAM_CODE, authCode)
        .encode()
        .build()
        .toUri();

    PaycoAccessTokenResponse response = restTemplate.getForObject(
        uri, PaycoAccessTokenResponse.class);
    if (response == null) {
      throw new OauthServerException("Payco access token response is null");
    }
    return response.getAccessToken();
  }

  /**
   * Payco 엑세스 토큰으로 사용자 정보를 요청.
   *
   * @param accessToken 엑세스 토큰
   * @return Oauth 사용자 정보
   */
  @Override
  public OauthUserInfo requestOauthUserInfo(String accessToken) {
    URI uri = UriComponentsBuilder
        .fromUriString(paycoOauthProperties.getUserInfoUrl())
        .queryParam(PARAM_ACCESS_TOKEN, accessToken)
        .encode()
        .build()
        .toUri();
    return restTemplate.getForObject(uri, PaycoUserInfo.class);
  }

}

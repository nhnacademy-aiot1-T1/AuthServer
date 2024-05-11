package com.nhnacademy.auth.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.auth.base.ServiceTest;
import com.nhnacademy.auth.dto.PaycoUserInfo;
import com.nhnacademy.auth.dto.domain.OauthUserInfo;
import com.nhnacademy.auth.properties.PaycoOauthProperties;
import com.nhnacademy.auth.service.dto.PaycoAccessTokenResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

class PaycoOauthServiceTest extends ServiceTest {

  @Mock
  private RestTemplate restTemplate;

  @Mock
  private PaycoOauthProperties paycoOauthProperties;

  @InjectMocks
  private PaycoOauthService paycoOauthService;

  @Nested
  class Payco_인증_코드로_엑세스_토큰을_요청 {

    @Test
    void 정상적인_authCode일_때_엑세스_토큰을_반환한다() {
      String authCode = "testAuthCode";
      PaycoAccessTokenResponse paycoAccessTokenResponse = new PaycoAccessTokenResponse("testAccessToken");

      when(paycoOauthProperties.getTokenUrl()).thenReturn("exampleUrl");

      when(restTemplate.getForObject(any(), eq(PaycoAccessTokenResponse.class))).thenReturn(paycoAccessTokenResponse);

      String result = paycoOauthService.requestAccessToken(authCode);

      verify(restTemplate, times(1)).getForObject(any(), eq(PaycoAccessTokenResponse.class));
      assertEquals("testAccessToken", result);
    }
  }

  @Nested
  class Payco_엑세스_토큰으로_사용자_정보를_요청 {

    @Test
    void 정상적인_accessToken일_때_사용자_정보를_반환한다() {
      String accessToken = "testAccessToken";
      PaycoUserInfo paycoUserInfo = Mockito.mock(PaycoUserInfo.class);
      when(paycoOauthProperties.getUserInfoUrl()).thenReturn("exampleUrl");

      when(restTemplate.getForObject(any(), eq(PaycoUserInfo.class))).thenReturn(paycoUserInfo);

      OauthUserInfo result = paycoOauthService.requestOauthUserInfo(accessToken);

      verify(restTemplate, times(1)).getForObject(any(), any());
      assertNotNull(result);
    }
  }
}
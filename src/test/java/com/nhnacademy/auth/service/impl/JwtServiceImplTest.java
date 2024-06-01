package com.nhnacademy.auth.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.auth.base.ServiceTest;
import com.nhnacademy.auth.dto.domain.UserInfo;
import com.nhnacademy.auth.entity.TokenIssuanceInfo;
import com.nhnacademy.auth.exception.ParseException;
import com.nhnacademy.auth.exception.TokenNotReissuableException;
import com.nhnacademy.auth.exception.UserAgentMismatchException;
import com.nhnacademy.auth.properties.JwtProperties;
import com.nhnacademy.auth.repository.AccessTokenRepository;
import com.nhnacademy.auth.common.DateHolder;
import com.nhnacademy.auth.common.UserAgentStore;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

class JwtServiceImplTest extends ServiceTest {

  @Mock
  private JwtProperties jwtProperties;

  @Mock
  private AccessTokenRepository accessTokenRepository;

  @Spy
  private ObjectMapper objectMapper;

  @Mock
  private UserAgentStore userAgentStore;

  @Mock
  private DateHolder dateHolder;

  @InjectMocks
  private JwtServiceImpl jwtServiceImpl;

  @BeforeEach
  void setUp() {

  }

  @Nested
  class 재발급_가능_여부_확인 {

    @Test
    void 토큰이_재발급_목록에_존재할_때_true를_반환한다() {
      when(accessTokenRepository.existsById(anyString())).thenReturn(true);

      boolean result = jwtServiceImpl.canReissue("existingToken");

      assertThat(result).isTrue();
    }

    @Test
    void 토큰이_재발급_목록에_존재하지_않을_때_false를_반환한다() {
      when(accessTokenRepository.existsById(anyString())).thenReturn(false);

      boolean result = jwtServiceImpl.canReissue("nonExistingToken");

      assertThat(result).isFalse();
    }


  }


  @Nested
  class 토큰_만료_처리 {

    @Test
    void 데이터베이스에_존재하는_토큰일_때_deleteById를_호출한다() {
      when(accessTokenRepository.existsById(anyString())).thenReturn(true);

      jwtServiceImpl.expireToken("nonExistingToken");

      verify(accessTokenRepository, times(1)).deleteById(anyString());
    }

    @Test
    void 데이터베이스에_존재하지_않는_토큰일_때_deleteById를_호출하지_않는다() {
      when(accessTokenRepository.existsById(anyString())).thenReturn(false);

      jwtServiceImpl.expireToken("nonExistingToken");

      verify(accessTokenRepository, times(0)).deleteById(anyString());
    }
  }

  @Nested
  class 토큰에서_userId_추출 {

    @Test
    void 유효한_토큰일때_추출_성공() {
      //given
      String testToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwidXNlcklkIjoiMTAwIiwiaWF0IjoxNTE2MjM5MDIyfQ.VBraIYqM-vTXH30YElyWVSXJ4gbFkYSg43E743C5x-E";
      //when
      Long userId = jwtServiceImpl.extractUserId(testToken);
      //then
      assertThat(userId).isEqualTo(100L);
    }

    @Test
    void 유효하지_않은_토큰일_때_ParseException_예외발생() {
      //given
      String testToken = "invalidToken";
      //when + then

      assertThatThrownBy(
          () -> jwtServiceImpl.extractUserId(testToken))
          .isInstanceOf(ParseException.class)
          .hasMessageContaining("구문 분석 오류가 발생했습니다.");
    }

  }

  @Nested
  class 토큰_발급_정보와_요청_시_정보_검증 {

    String issuedBrowser = "Chrome";
    String issuedIp = "192.0.0.1";
    TokenIssuanceInfo tokenInfo;

    @BeforeEach
    void setUp() {
      tokenInfo = TokenIssuanceInfo.builder()
          .browser(issuedBrowser)
          .ip(issuedIp)
          .build();
    }

    @Test
    void 토큰의_발급_시_IP_브라우저_정보와_요청_IP_브라우저_정보가_일치할_때_예외가_발생하지_않는다() {
      //given
      String requestBrowser = "Chrome";
      String requestIp = "192.0.0.1";
      when(userAgentStore.getUserBrowser()).thenReturn(requestBrowser);
      when(userAgentStore.getUserIp()).thenReturn(requestIp);
      //when + then
      assertThatNoException().isThrownBy(() -> jwtServiceImpl.validateLocationChanged(tokenInfo));
    }

    @Test
    void 토큰의_발급_시_IP와_요청_IP가_일치하지_않을_때_UserAgentMismatchException_예외가_발생한다() {
      //given
      String requestBrowser = "Chrome";
      String requestIp = "192.0.0.2";
      when(userAgentStore.getUserBrowser()).thenReturn(requestBrowser);
      when(userAgentStore.getUserIp()).thenReturn(requestIp);
      //when + then
      assertThatThrownBy(() -> jwtServiceImpl.validateLocationChanged(tokenInfo))
          .isInstanceOf(UserAgentMismatchException.class);
    }

    @Test
    void 토큰의_발급_시_브라우저와_요청_브라우저가_일치하지_않을_때_UserAgentMismatchException_예외가_발생한다() {
      //given
      String requestBrowser = "FireFox";
      when(userAgentStore.getUserBrowser()).thenReturn(requestBrowser);
      //when + then
      assertThatThrownBy(() -> jwtServiceImpl.validateLocationChanged(tokenInfo))
          .isInstanceOf(UserAgentMismatchException.class);
    }
  }

  @Nested
  class 토큰_발급정보_저장소에서_토큰_발급정보_조회 {

    @Test
    void 토큰_정보가_존재할_때_발급정보를_반환한다() {
      //given
      when(accessTokenRepository.findById(anyString())).thenReturn(
          Optional.of(TokenIssuanceInfo.builder().build()));
      //when
      TokenIssuanceInfo tokenIssuanceInfo = jwtServiceImpl.getTokenIssuanceInfo("existingToken");
      //then
      assertThat(tokenIssuanceInfo).isNotNull();
    }

    @Test
    void 토큰_정보가_존재하지_않을_때_TokenNotReissuableException_예외가_발생한다() {
      //given
      when(accessTokenRepository.findById(anyString())).thenReturn(Optional.empty());
      //when + then
      assertThatThrownBy(() -> jwtServiceImpl.getTokenIssuanceInfo("nonExistingToken"))
          .isInstanceOf(TokenNotReissuableException.class);
    }
  }

  @Nested
  class 토큰_발급 {

    UserInfo user;
    @BeforeEach
    void setUp() {
      user = UserInfo.builder()
          .id(1L)
          .name("홍길동")
          .role(UserInfo.Role.USER)
          .build();

      Date fixedDate = Date.from(Instant.parse("2000-01-01T00:00:00Z"));
      when(dateHolder.now()).thenReturn(fixedDate);
    }

    @Test
    void 토큰_발급_성공() {
      //given
      String exceptedToken = "eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOjEsIm5hbWUiOiLtmY3quLjrj5kiLCJyb2xlIjoiVVNFUiIsImlhdCI6OTQ2Njg0ODAwLCJleHAiOjk0NjY4NTEwMH0.FyiB09xw-DBY9Hf6Vlg4OJLpynpMyY29Int19w1KKxC6podqqgPaInC3V4EFCo6w";
      when(userAgentStore.getUserIp()).thenReturn("");
      when(userAgentStore.getUserBrowser()).thenReturn("");
      when(jwtProperties.getSecret()).thenReturn("dGVzdHRlc3R0ZXN0dGVzdHRlc3R0ZXN0dGVzdHRlc3R0ZXN0dGVzdHRlc3Q=");
      //when
      String token = jwtServiceImpl.issueJwt(user);
      //then
      assertThat(token).isEqualTo(exceptedToken);
    }
  }
}
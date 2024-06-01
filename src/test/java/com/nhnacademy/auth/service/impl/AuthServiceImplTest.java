package com.nhnacademy.auth.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.auth.base.ServiceTest;
import com.nhnacademy.auth.dto.domain.UserCredentials;
import com.nhnacademy.auth.dto.domain.UserInfo;
import com.nhnacademy.auth.entity.TokenIssuanceInfo;
import com.nhnacademy.auth.exception.PasswordNotMatchException;
import com.nhnacademy.auth.exception.TokenNotReissuableException;
import com.nhnacademy.auth.service.AccountService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

class AuthServiceImplTest extends ServiceTest {

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private AccountService accountService;

  @Mock
  private JwtServiceImpl jwtServiceImpl;

  @InjectMocks
  private AuthServiceImpl authService;

  @Nested
  class 로그인 {

    @Test
    void 아이디와_패스워드가_사용자와_일치할_때_액세스토큰을_발급한다() {
      String loginId = "testUser";
      String password = "testPassword";
      String expectedToken = "expectedToken";
      UserCredentials userCredentials = UserCredentials.builder()
          .id(1L)
          .loginId("testUser")
          .password("testPassword")
          .build();
      when(accountService.getUserCredentialsByLoginId(anyString())).thenReturn(userCredentials);
      when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
      when(jwtServiceImpl.issueJwt(any())).thenReturn(expectedToken);

      String accessToken = authService.login(loginId, password);

      assertThat(accessToken).isEqualTo(expectedToken);
    }

    @Test
    void 비밀번호가_일치하지_않을_때_예외를_발생시킨다() {
      String loginId = "testUser";
      String password = "testPassword2";
      UserCredentials userCredentials = UserCredentials.builder()
          .id(1L)
          .loginId("testUser")
          .password("testPassword")
          .build();
      when(accountService.getUserCredentialsByLoginId(anyString())).thenReturn(userCredentials);
      when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

      assertThrows(PasswordNotMatchException.class, () -> authService.login(loginId, password));
    }
  }

  @Nested
  class 로그아웃 {

    @Test
    void 로그아웃_요청이_들어왔을_때_토큰을_만료시키는_메서드를_호출한다() {
      doNothing().when(jwtServiceImpl).expireToken(anyString());
      authService.logout("logoutToken");
      verify(jwtServiceImpl, times(1)).expireToken(anyString());
    }

    @Nested
    class 토큰_재발급 {

      @Test
      void 만료된_토큰을_받아_토큰을_발급받은_IP와_브라우저를_비교해_같은_곳에서_재발급_요청이_오면_새로운_토큰을_발급한다() {
        UserInfo userInfo = UserInfo.builder().build();
        when(jwtServiceImpl.canReissue(anyString())).thenReturn(true);
        when(jwtServiceImpl.getTokenIssuanceInfo(anyString())).thenReturn(new TokenIssuanceInfo());
        when(jwtServiceImpl.extractUserId(anyString())).thenReturn(1L);
        when(accountService.getUserInfoById(anyLong())).thenReturn(userInfo);
        when(jwtServiceImpl.issueJwt(any(UserInfo.class))).thenReturn("reissuedToken");

        String newToken = authService.reissueToken("expiredToken");

        assertEquals("reissuedToken", newToken);
      }

      @Test
      void 재발급_불가능한_토큰일_때_예외를_발생시킨다() {
        when(jwtServiceImpl.canReissue(anyString())).thenReturn(false);
        assertThrows(TokenNotReissuableException.class,
            () -> authService.reissueToken("invalidToken"));
      }
    }
  }
}
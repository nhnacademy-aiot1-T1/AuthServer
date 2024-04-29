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

import com.nhnacademy.auth.adaptor.AccountAdapter;
import com.nhnacademy.auth.dto.domain.UserDto;
import com.nhnacademy.auth.entity.TokenIssuanceInfo;
import com.nhnacademy.auth.exception.PasswordNotMatchException;
import com.nhnacademy.auth.exception.TokenNotReissuableException;
import com.nhnacademy.auth.exception.UserNotFoundException;
import com.nhnacademy.common.dto.CommonResponse;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AuthServiceImplTest {

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private AccountAdapter accountAdapter;

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
      UserDto user = UserDto.builder()
          .id(1L)
          .loginId("testUser")
          .password("testPassword")
          .name("홍길동")
          .email("")
          .build();
      when(accountAdapter.getAccountInfo(anyString())).thenReturn(CommonResponse.success(user));
      when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
      when(jwtServiceImpl.issueJwt(any(UserDto.class))).thenReturn(expectedToken);

      String accessToken = authService.login(loginId, password);

      assertThat(accessToken).isEqualTo(expectedToken);
    }

    @Test
    void 비밀번호가_일치하지_않을_때_예외를_발생시킨다() {
      String loginId = "testUser";
      String password = "testPassword";
      UserDto user = UserDto.builder()
          .id(1L)
          .loginId("testUser")
          .password("testPassword")
          .name("홍길동")
          .email("")
          .build();
      when(accountAdapter.getAccountInfo(anyString())).thenReturn(CommonResponse.success(user));
      when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

      assertThrows(PasswordNotMatchException.class, () -> authService.login(loginId, password));
    }

    @Test
    void 아이디에_해당하는_유저가_존재하지_않을_때_예외를_발생시킨다() {
      String loginId = "testUser";
      String password = "testPassword";
      when(accountAdapter.getAccountInfo(anyString())).thenReturn(
          CommonResponse.fail("해당하는 유저가 존재하지 않습니다."));

      assertThrows(UserNotFoundException.class, () -> authService.login(loginId, password));
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
        CommonResponse<UserDto> userResponse = CommonResponse.success(UserDto.builder().build());
        when(jwtServiceImpl.canReissue(anyString())).thenReturn(true);
        when(jwtServiceImpl.getTokenIssuanceInfo(anyString())).thenReturn(new TokenIssuanceInfo());
        when(jwtServiceImpl.extractUserId(anyString())).thenReturn(1L);
        when(accountAdapter.getUserInfo(anyLong())).thenReturn(userResponse);
        when(jwtServiceImpl.issueJwt(any(UserDto.class))).thenReturn("reissuedToken");

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
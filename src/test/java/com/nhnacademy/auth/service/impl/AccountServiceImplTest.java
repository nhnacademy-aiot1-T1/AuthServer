package com.nhnacademy.auth.service.impl;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import com.nhnacademy.auth.adaptor.AccountAdapter;
import com.nhnacademy.auth.dto.domain.UserCredentials;
import com.nhnacademy.auth.dto.domain.UserInfo;
import com.nhnacademy.auth.exception.UserNotFoundException;
import com.nhnacademy.auth.service.dto.request.OauthUserRegisterRequest;
import com.nhnacademy.common.dto.CommonResponse;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AccountServiceImplTest {

  @Mock
  private AccountAdapter accountAdapter;

  @InjectMocks
  private AccountServiceImpl accountService;

  @Nested
  class 로그인_ID로_사용자_자격증명_조회 {

    @Test
    void 사용자_자격증명이_존재할_때_자격증명을_반환한다() {
      // given
      UserCredentials mockUserCredentials = Mockito.mock(UserCredentials.class);
      CommonResponse<UserCredentials> commonResponse = CommonResponse.success(mockUserCredentials);

      when(accountAdapter.getUserCredentialsByLoginId(anyString())).thenReturn(commonResponse);
      // when
      UserCredentials result = accountService.getUserCredentialsByLoginId("existingLoginId");
      // then
      assertThat(result).isNotNull();
    }

    @Test
    void 사용자_자격증명이_존재하지_않을_때_UserNotFoundException_예외가_발생한다() {
      // given
      when(accountAdapter.getUserCredentialsByLoginId(anyString())).thenReturn(CommonResponse.fail(""));

      // when, then
      assertThatThrownBy(() -> accountService.getUserCredentialsByLoginId("nonExistingLoginId"))
          .isInstanceOf(UserNotFoundException.class);
    }
  }

  @Nested
  class ID로_사용자_정보_조회 {

    @Test
    void 사용자_존재할_때_사용자_정보를_반환한다() {
      // given
      UserInfo mockUserInfo = Mockito.mock(UserInfo.class);
      when(accountAdapter.getUserInfoById(anyLong())).thenReturn(CommonResponse.success(mockUserInfo));

      // when
      UserInfo result = accountService.getUserInfoById(1L);

      // then
      assertThat(result).isNotNull();
    }

    @Test
    void 사용자가_존재하지_않을_때_UserNotFoundException_예외가_발생한다() {
      // given
      when(accountAdapter.getUserInfoById(anyLong())).thenReturn(CommonResponse.fail(null));

      // when, then
      assertThatThrownBy(() -> accountService.getUserInfoById(1L))
          .isInstanceOf(UserNotFoundException.class);
    }
  }

  @Nested
  class Oauth_ID로_사용자_정보_조회 {

    @Test
    void 사용자가_존재할_때_사용자_정보를_반환한다() {
      UserInfo mockUserInfo = Mockito.mock(UserInfo.class);
      when(accountAdapter.getUserInfoByOauthId(anyString())).thenReturn(CommonResponse.success(mockUserInfo));

      UserInfo result = accountService.getUserInfoByOauthId("existingOauthId");

      assertThat(result).isNotNull();
    }

    @Test
    void 사용자가_존재하지_않을_때_null을_반환한다() {
      when(accountAdapter.getUserInfoByOauthId(anyString())).thenReturn(CommonResponse.fail(null));

      UserInfo result = accountService.getUserInfoByOauthId("nonExistingOauthId");

      assertThat(result).isNull();
    }
  }

  @Nested
  class Oauth_사용자_등록 {

    @Test
    void 사용자_등록_성공() {
      // given
      UserInfo mockUserInfo = Mockito.mock(UserInfo.class);
      when(accountAdapter.registerOauthUser(any(OauthUserRegisterRequest.class))).thenReturn(CommonResponse.success(mockUserInfo));

      UserInfo result = accountService.registerOauthUser("oauthType", "oauthId", "name", "email");

      assertThat(result).isNotNull();
    }
  }
}

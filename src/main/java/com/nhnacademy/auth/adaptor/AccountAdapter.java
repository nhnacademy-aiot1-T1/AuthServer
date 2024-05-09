package com.nhnacademy.auth.adaptor;

import com.nhnacademy.auth.adaptor.decoder.AccountFeignDecoder;
import com.nhnacademy.auth.dto.domain.UserCredentials;
import com.nhnacademy.auth.dto.domain.UserInfo;
import com.nhnacademy.auth.service.dto.request.OauthUserRegisterRequest;
import com.nhnacademy.common.dto.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * Account 서비스와 통신하기 위한 Feign Client.
 */
@FeignClient(
    value = "ACCOUNT-SERVICE",
    path = "/api/account",
    configuration = AccountFeignDecoder.class,
    decode404 = true
)
public interface AccountAdapter {

  /**
   * ACCOUNT-SERVICE 에 login ID로 유저 인증 정보를 조회.
   *
   * @param loginId 로그인 아이디
   * @return 유저 정보
   */
  @GetMapping("/users/{loginId}/credentials")
  CommonResponse<UserCredentials> getUserCredentialsByLoginId(@PathVariable String loginId);

  /**
   * ACCOUNT-SERVICE 에 oauthId로 유저 정보를 조회.
   *
   * @param oauthId oauthId
   * @return 유저 정보
   */
  @GetMapping("/oauth/users/{oauthId}")
  CommonResponse<UserInfo> getUserInfoByOauthId(@PathVariable String oauthId);

  /**
   * ACCOUNT-SERVICE 에 유저 ID로 유저 정보를 조회.
   *
   * @param id 유저 id
   * @return 유저 정보
   */
  @GetMapping("/users/{id}")
  CommonResponse<UserInfo> getUserInfoById(@PathVariable Long id);

  @PostMapping("/users")
  CommonResponse<UserInfo> registerOauthUser(OauthUserRegisterRequest request);
}
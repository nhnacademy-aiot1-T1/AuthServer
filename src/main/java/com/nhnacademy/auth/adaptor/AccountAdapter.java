package com.nhnacademy.auth.adaptor;

import com.nhnacademy.auth.adaptor.decoder.AccountFeignDecoder;
import com.nhnacademy.auth.dto.domain.UserDto;
import com.nhnacademy.common.dto.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * Account 서비스와 통신하기 위한 Feign Client.
 */
@FeignClient(value = "ACCOUNT-SERVICE", path = "/api/users", configuration = AccountFeignDecoder.class)
public interface AccountAdapter {

  /**
   * ACCOUNT-SERVICE 에 login ID로 유저 정보를 조회한다.
   *
   * @param loginId 로그인 아이디
   * @return  유저 정보
   */
  @GetMapping(value = "/{loginId}/auth")
  CommonResponse<UserDto> getAccountInfo(@PathVariable String loginId);

  /**
   * ACCOUNT-SERVICE 에 유저 ID로 유저 정보를 조회.
   *
   * @param id 유저 id
   * @return 유저 정보
   */
  @GetMapping("/{id}/info")
  CommonResponse<UserDto> getUserInfo(@PathVariable Long id);

}
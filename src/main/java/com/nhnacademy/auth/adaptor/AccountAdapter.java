package com.nhnacademy.auth.adaptor;

import com.nhnacademy.auth.dto.LoginInfo;
import com.nhnacademy.auth.dto.User;
import com.nhnacademy.common.dto.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value = "ACCOUNT-SERVICE", path = "/api/users")
public interface AccountAdapter {

  @GetMapping("/{id}/auth")
  CommonResponse<LoginInfo> getAccountInfo(@PathVariable String id);

  @GetMapping("/{id}/info")
  CommonResponse<User> getUserInfo(@PathVariable String id);
}
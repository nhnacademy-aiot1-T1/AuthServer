package com.nhnacademy.auth.adapter;

import com.nhnacademy.auth.dto.User;
import com.nhnacademy.auth.dto.LoginInfo;
import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "account-api", path = "/api/account")
public interface AccountAdapter {
  @GetMapping("/login/{id}")
  Optional<LoginInfo> getAccountInfo(@PathVariable String id);

  @GetMapping("/users/{id}")
  User getUserInfo(@PathVariable String id);
}

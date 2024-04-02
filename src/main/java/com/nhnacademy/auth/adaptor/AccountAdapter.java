package com.nhnacademy.auth.adaptor;

import com.nhnacademy.auth.dto.LoginInfo;
import com.nhnacademy.auth.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(value = "account-api", path = "/api/account")
public interface AccountAdapter {
    @GetMapping("/login/{id}")
    Optional<LoginInfo> getAccountInfo(@PathVariable String id);

    @GetMapping("/users/{id}")
    User getUserInfo(@PathVariable String id);
}
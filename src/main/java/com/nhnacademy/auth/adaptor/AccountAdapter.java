package com.nhnacademy.auth.adaptor;

import com.nhnacademy.auth.dto.LoginInfo;
import com.nhnacademy.auth.dto.Response;
import com.nhnacademy.auth.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value = "account-api", path = "/api/users")
public interface AccountAdapter {
    @GetMapping("/{id}/auth")
    Response<LoginInfo> getAccountInfo(@PathVariable String id);

    @GetMapping("/{id}/info")
    Response<User> getUserInfo(@PathVariable String id);
}
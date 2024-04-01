package com.nhnacademy.auth.controller;

import com.nhnacademy.auth.dto.LoginInfo;
import com.nhnacademy.auth.dto.ResponseFormat;
import com.nhnacademy.auth.service.LoginService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
  private final LoginService loginService;

  @PostMapping
  public ResponseFormat login(@RequestBody LoginInfo info) {
    if (loginService.match(info)) {
      // FIXME : JWT 토큰 발급 로직 작성해주세요
    } else {
      // FIXME : 실패 로직 작성해 주세요
    }

    return ResponseFormat.builder()
        .status("success")
        .data(null) //FIXME : LoginResponse 추가해주세요~
        .message(null)
        .localDateTime(LocalDateTime.now())
        .build();
  }
}

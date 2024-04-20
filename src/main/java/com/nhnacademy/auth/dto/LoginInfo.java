package com.nhnacademy.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginInfo {

  private Long id;
  private String loginId;
  private String password;

}
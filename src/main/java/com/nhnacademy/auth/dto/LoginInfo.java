package com.nhnacademy.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginInfo {

  private String id;
  private String password;
  private String ip;

}
package com.nhnacademy.auth.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OauthLoginRequest {

  private final String type;
  private final String authCode;

}

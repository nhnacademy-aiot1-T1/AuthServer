package com.nhnacademy.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegenerateAccessTokenDto {
  private String id;
  private String ip;
  private String browser;
  private String legacyAccessToken;
}


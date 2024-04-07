package com.nhnacademy.auth.dto;

import lombok.Getter;

@Getter
public class JwtPayloadDto {
  private String userId;
  private String userRole;
  private String userIp;
  private String iat;
  private String exp;

}

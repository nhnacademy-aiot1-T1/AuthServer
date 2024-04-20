package com.nhnacademy.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Account api에서 받을 user 정보
 */
@Getter
@ToString
@AllArgsConstructor
public class User {

  private Long id;
  private String name;
  private String email;
  private String authType;
  private Status status;
  private Role role;

  /**
   * User status 2종.
   * <li> ADMIN
   * <li> USER
   */
  public enum Role {
    ADMIN,
    USER
  }

  /**
   * User Role 2종.
   * <li> ACTIVE
   * <li> DEACTIVATE
   */
  public enum Status {
    ACTIVE,
    DEACTIVATE
  }
}
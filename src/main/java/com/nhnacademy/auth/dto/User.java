package com.nhnacademy.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Account api에서 받을 user 정보
 */
@Getter
@AllArgsConstructor
public class User {

  private String id;
  private Status status;
  private Role role;

  /**
   * User status 2종.
   * <li> ADMIN
   * <li> USER
   */
  public enum Status {
    ADMIN,
    USER
  }

  /**
   * User Role 2종.
   * <li> ACTIVE
   * <li> DEACTIVATE
   */
  public enum Role {
    ACTIVE,
    DEACTIVATE
  }
}
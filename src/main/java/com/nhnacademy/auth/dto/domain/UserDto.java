package com.nhnacademy.auth.dto.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Account api에서 받을 user 정보.
 */
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {

  private Long id;
  private String loginId;
  private String password;
  private String name;
  private String email;
  private String authType;
  private Role role;

  @Builder
  public UserDto(Long id, String loginId, String password, String name, String email,
      String authType, Role role) {
    this.id = id;
    this.loginId = loginId;
    this.password = password;
    this.name = name;
    this.email = email;
    this.authType = authType;
    this.role = role;
  }

  /**
   * UserDto status 2종.
   * <li> ADMIN
   * <li> USER
   */
  public enum Role {
    ADMIN,
    USER
  }

  /**
   * UserDto Role 2종.
   * <li> ACTIVE
   * <li> DEACTIVATE
   */
  public enum Status {
    ACTIVE,
    DEACTIVATE
  }
}
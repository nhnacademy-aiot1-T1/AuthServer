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
public class UserInfo {

  private Long id;
  private String name;
  private Role role;

  @Builder
  public UserInfo(Long id, String name, Role role) {
    this.id = id;
    this.name = name;
    this.role = role;
  }

  /**
   * UserInfo status 2종.
   * <li> ADMIN
   * <li> USER
   */
  public enum Role {
    ADMIN, USER
  }

}
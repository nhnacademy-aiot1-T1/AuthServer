package com.nhnacademy.auth.dto;

import com.nhnacademy.auth.dto.domain.OauthUserInfo;
import java.util.Map;

/**
 * Payco 사용자 정보.
 */
public class PaycoUserInfo implements OauthUserInfo {

  private static final String MEMBER_KEY = "member";

  private final Map<String, Map<String, String>> data;

  public PaycoUserInfo(Map<String, Map<String, String>> data) {
    this.data = data;
  }

  @Override
  public String getName() {
    return data.get(MEMBER_KEY).get("name");
  }

  @Override
  public String getEmail() {
    return data.get(MEMBER_KEY).get("email");
  }

  @Override
  public String getOauthId() {
    return data.get(MEMBER_KEY).get("idNo");
  }
}
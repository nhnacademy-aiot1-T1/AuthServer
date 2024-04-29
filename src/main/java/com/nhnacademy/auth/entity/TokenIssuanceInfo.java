package com.nhnacademy.auth.entity;


import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 액세스 토큰 Entity.
 */
@Getter
@Setter
@Table(name = "access_token")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TokenIssuanceInfo {

  @Id
  @Column(name = "access_token")
  private String accessToken;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "ip")
  private String ip;

  @Column(name = "browser")
  private String browser;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  /**
   * 액세스 토큰 생성자.
   *
   * @param accessToken : 액세스 토큰
   * @param userId : userId
   * @param ip : userIp
   * @param browser : userBrowser
   */
  @Builder
  public TokenIssuanceInfo(String accessToken, Long userId, String ip, String browser) {
    this.accessToken = accessToken;
    this.userId = userId;
    this.ip = ip;
    this.browser = browser;
    this.createdAt = LocalDateTime.now();
  }
}

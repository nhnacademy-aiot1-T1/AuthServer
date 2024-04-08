package com.nhnacademy.auth.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = "access_token")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AccessToken {

  @Id
  @Column(name = "access_token")
  private String token;

  @Column(name = "user_id")
  private String userId;

  @Column(name = "ip")
  private String ip;
}

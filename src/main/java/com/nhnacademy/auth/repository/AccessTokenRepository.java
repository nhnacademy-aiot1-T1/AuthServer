package com.nhnacademy.auth.repository;

import com.nhnacademy.auth.domain.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessTokenRepository extends JpaRepository<AccessToken, String> {

  String findByToken(String token);

}

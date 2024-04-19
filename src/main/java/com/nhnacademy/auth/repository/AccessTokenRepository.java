package com.nhnacademy.auth.repository;

import com.nhnacademy.auth.domain.AccessToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessTokenRepository extends JpaRepository<AccessToken, String> {

  interface IpAndBrowser{
    String getIp();
    String getBrowser();
  }

  Optional<IpAndBrowser> findByAccessToken(String accessToken);

}

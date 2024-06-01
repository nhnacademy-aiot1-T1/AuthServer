package com.nhnacademy.auth.repository;

import com.nhnacademy.auth.entity.TokenIssuanceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TokenIssuanceInfo 엔티티의 JPA Repository.
 */
public interface AccessTokenRepository extends JpaRepository<TokenIssuanceInfo, String> {

  

}

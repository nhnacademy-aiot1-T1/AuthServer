package com.nhnacademy.auth.service.impl;

import com.nhnacademy.auth.adaptor.AccountAdapter;
import com.nhnacademy.auth.dto.domain.UserDto;
import com.nhnacademy.auth.entity.TokenIssuanceInfo;
import com.nhnacademy.auth.exception.PasswordNotMatchException;
import com.nhnacademy.auth.exception.TokenNotReissuableException;
import com.nhnacademy.auth.exception.UserNotFoundException;
import com.nhnacademy.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 로그인, 로그아웃, 토큰 재발급을 담당하는 서비스 클래스.
 */
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final PasswordEncoder passwordEncoder;
  private final AccountAdapter accountAdapter;
  private final JwtServiceImpl jwtServiceImpl;

  /**
   * 로그인에 성공했을 경우 : accessToken 발급.
   *
   * @param loginId  : 로그인 아이디
   * @param password : 비밀번호
   * @return : accessToken
   */
  @Override
  public String login(final String loginId, final String password) {
    UserDto user = accountAdapter.getAccountInfo(loginId)
        .dataOrElseThrow(UserNotFoundException::new);
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new PasswordNotMatchException(loginId);
    }
    return jwtServiceImpl.issueJwt(user);
  }

  @Override
  public String oauthLogin(final String loginId) {
    UserDto user = accountAdapter.getAccountInfo(loginId)
        .dataOrElseThrow(UserNotFoundException::new);
    return jwtServiceImpl.issueJwt(user);
  }

  @Override
  public void logout(String accessToken) {
    jwtServiceImpl.expireToken(accessToken);
  }

  /**
   * 만료된 토큰을 받아 토큰을 발급 받은 IP와 브라우저를 비교해 같은 곳에서 재발급 요청이 오면 새로운 토큰을 발급.
   *
   * @param expiredToken : 만료된 토큰
   * @return : 재발급된 토큰
   */
  @Override
  public String reissueToken(String expiredToken) {
    // 재발급 가능한 토큰인지 확인
    if (!jwtServiceImpl.canReissue(expiredToken)) {
      throw new TokenNotReissuableException();
    }
    TokenIssuanceInfo tokenIssueInfo = jwtServiceImpl.getTokenIssuanceInfo(expiredToken);
    // 토큰 발급 시의 IP, 브라우저 정보와 요청한 정보가 같은지 확인
    jwtServiceImpl.validateLocationChanged(tokenIssueInfo);
    // 토큰에서 userId 추출
    Long userId = jwtServiceImpl.extractUserId(expiredToken);
    // userId로 사용자 정보 조회
    UserDto user = accountAdapter.getUserInfo(userId)
        .dataOrElseThrow(UserNotFoundException::new);
    // 사용자 정보로 토큰 재발급
    String reissueToken = jwtServiceImpl.issueJwt(user);
    // 만료된 토큰 삭제
    jwtServiceImpl.expireToken(expiredToken);
    return reissueToken;
  }


}

package com.nhnacademy.auth.controller;

import com.nhnacademy.auth.dto.LoginInfo;
import com.nhnacademy.auth.dto.LoginResponse;
import com.nhnacademy.auth.dto.RegenerateAccessTokenDto;
import com.nhnacademy.auth.dto.User;
import com.nhnacademy.auth.exception.PasswordNotMatchException;
import com.nhnacademy.auth.service.JwtTokenService;
import com.nhnacademy.auth.service.LoginService;
import com.nhnacademy.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * default path : /api/auth/login
 * <p>
 * method is :
 * </p>
 * <li> login : /pc
 * <li> loginMobile : /mobile
 * <li> regenerateAccessToken : /regenerate
 * rest controller class.
 */
@Slf4j
@RestController
@RequestMapping("/api/auth/login")
@RequiredArgsConstructor
public class LoginController {

  private final LoginService loginService;
  private final JwtTokenService jwtTokenService;

  private static final String CONTENT_TYPE = "Content-Type";
  private static final String APPLICATION_JSON = "application/json";
  private static final String SUCCESS = "success";

  /**
   * <li>로그인에 성공했을 경우 : userId, userRole, accessToken 발급.
   * <p>
   * <li>로그인에 실패했을 경우 : exception.
   * </p>
   *
   * @param info : id, password, ip
   * @return : LoginResponse
   * @Exception : PasswordNotMatchException(userId)
   */
  @PostMapping("/pc")
  public ResponseEntity<CommonResponse<LoginResponse>> login(@RequestBody LoginInfo info) {
    if (!loginService.match(info)) {
      throw new PasswordNotMatchException(info.getId());
    }

    User user = loginService.getUser(info.getId());
    String accessToken = jwtTokenService.generateAccessToken(user, info.getIp());

    log.error("in login", accessToken);
    LoginResponse loginResponse = new LoginResponse(user.getId(), accessToken);

    log.info("login response dto is : {}, :{}", loginResponse.getUserId(),
        loginResponse.getAccessToken());

    return ResponseEntity.status(HttpStatus.CREATED)
        .header(CONTENT_TYPE, APPLICATION_JSON)
        .body(CommonResponse.success(loginResponse, SUCCESS));
  }

  /**
   * <li>mobile 환경에서 로그인에 성공했을 경우, 토큰 발급.
   * <p>
   * <li>로그인에 실패했을 경우: exception
   * </p>
   *
   * @param info : id, password, ip
   * @return : LoginResponse
   * @Exception : PasswordNotMatchException(userId)
   */
  @PostMapping("/mobile")
  public ResponseEntity<CommonResponse<LoginResponse>> loginMobile(@RequestBody LoginInfo info) {
    if (!loginService.match(info)) {
      throw new PasswordNotMatchException(info.getId());
    }

    User user = loginService.getUser(info.getId());
    String accessToken = jwtTokenService.generateJwtTokenFromMobile(user, info.getIp());

    LoginResponse loginResponse = new LoginResponse(user.getId(), user.getRole(), accessToken);

    log.info("login response dto is : {}, :{}", loginResponse.getUserId(),
        loginResponse.getAccessToken());

    return ResponseEntity.status(HttpStatus.CREATED)
        .header(CONTENT_TYPE, APPLICATION_JSON)
        .body(CommonResponse.success(loginResponse, SUCCESS));
  }

  /**
   * <li> 토큰의 재발급 로직.
   *
   * @param regenerateAccessTokenDto : id, ip, legacyAccessToken
   * @return : String
   * @Exception : IpIsNotEqualsException
   */
  @PostMapping(value = "/regenerate")
  public ResponseEntity<CommonResponse<String>> regenerateAccessToken(@RequestBody
      RegenerateAccessTokenDto regenerateAccessTokenDto) {

    String regenerateAccessToken = jwtTokenService.regenerateAccessToken(
        regenerateAccessTokenDto.getIp(),
        regenerateAccessTokenDto.getLegacyAccessToken());

    return ResponseEntity.status(HttpStatus.CREATED)
        .header(CONTENT_TYPE, APPLICATION_JSON)
        .body(CommonResponse.success(regenerateAccessToken, SUCCESS));
  }
}

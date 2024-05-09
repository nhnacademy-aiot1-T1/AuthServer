package com.nhnacademy.auth.controller;

import com.nhnacademy.auth.controller.dto.request.LoginRequest;
import com.nhnacademy.auth.controller.dto.request.LogoutRequest;
import com.nhnacademy.auth.controller.dto.request.OauthLoginRequest;
import com.nhnacademy.auth.controller.dto.request.ReissueRequest;
import com.nhnacademy.auth.controller.dto.response.LoginResponse;
import com.nhnacademy.auth.service.AuthService;
import com.nhnacademy.common.dto.CommonResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private static final String LOGIN_SUCCESS_MESSAGE = "login success";
  private static final String LOGOUT_SUCCESS_MESSAGE = "logout success";
  private static final String REISSUE_SUCCESS_MESSAGE = "reissue success";
  private static final String EXPIRE_SUCCESS_MESSAGE = "expire success";

  /**
   * <li>로그인에 성공했을 경우 : userId, userRole, accessToken 발급.
   * <li>로그인에 실패했을 경우 : exception.
   *
   * @param loginRequest : id, password, ip
   * @return : LoginResponse
   */
  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public CommonResponse<LoginResponse> login(
      @RequestBody @Valid LoginRequest loginRequest) {
    String loginId = loginRequest.getLoginId();
    String password = loginRequest.getPassword();
    String accessToken = authService.login(loginId, password);
    LoginResponse loginResponse = new LoginResponse(accessToken);
    return CommonResponse.success(loginResponse, LOGIN_SUCCESS_MESSAGE);
  }

  /**
   * OAuth 로그인 요청을 받아서 액세스 토큰을 반환.
   *
   * @param loginRequest :OAuth 로그인 객체
   * @return  : LoginResponse:
   */
  @PostMapping("/oauth-login")
  @ResponseStatus(HttpStatus.OK)
  public CommonResponse<LoginResponse> oauthLogin(
      @RequestBody @Valid OauthLoginRequest loginRequest) {
    String type = loginRequest.getType();
    String authCode = loginRequest.getAuthCode();
    String accessToken = authService.oauthLogin(type, authCode);
    LoginResponse loginResponse = new LoginResponse(accessToken);
    return CommonResponse.success(loginResponse, LOGIN_SUCCESS_MESSAGE);
  }

  /**
   * 로그아웃 요청을 받아 토큰을 만료.
   *
   * @param logoutRequest : 로그아웃할 사용자의 토큰
   * @return : no content
   */
  @PostMapping("/logout")
  @ResponseStatus(HttpStatus.OK)
  public CommonResponse<String> logout(@RequestBody @Valid LogoutRequest logoutRequest) {
    String accessToken = logoutRequest.getAccessToken();
    authService.logout(accessToken);
    return CommonResponse.success(null, LOGOUT_SUCCESS_MESSAGE);
  }

  /**
   * 토큰 재발급 요청을 받아 토큰 재발급.
   *
   * @param reissueRequest : 만료된 토큰
   * @return : 재발급된 토큰
   */
  @PostMapping("/reissue")
  public CommonResponse<String> reissue(@RequestBody @Valid ReissueRequest reissueRequest) {
    String expiredToken = reissueRequest.getExpiredToken();
    String reissuedToken = authService.reissueToken(expiredToken);
    return CommonResponse.success(reissuedToken, REISSUE_SUCCESS_MESSAGE);
  }

  /**
   * 토큰 만료 요청을 받아 토큰 만료.
   *
   * @return : 만료시킬 토큰
   */
  @PostMapping("/expire")
  public CommonResponse<String> expire() {
    return CommonResponse.success(null, EXPIRE_SUCCESS_MESSAGE);
  }

}

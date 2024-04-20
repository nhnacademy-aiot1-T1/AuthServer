package com.nhnacademy.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.auth.dto.LoginInfo;
import com.nhnacademy.auth.dto.LoginResponse;
import com.nhnacademy.auth.dto.RegenerateAccessTokenDto;
import com.nhnacademy.auth.dto.User;
import com.nhnacademy.auth.service.AccessTokenService;
import com.nhnacademy.auth.service.JwtTokenService;
import com.nhnacademy.auth.service.LoginService;
import com.nhnacademy.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

  private final LoginService loginService;
  private final JwtTokenService jwtTokenService;
  private final AccessTokenService accessTokenService;

  private static final String CONTENT_TYPE = HttpHeaders.CONTENT_TYPE;
  private static final String APPLICATION_JSON = MediaType.APPLICATION_JSON_VALUE;
  private static final String LOGIN_SUCCESS_MESSAGE = "login success";
  private static final String LOGOUT_SUCCESS_MESSAGE = "logout success";

  /**
   * <li>로그인에 성공했을 경우 : userId, userRole, accessToken 발급.
   * <li>로그인에 실패했을 경우 : exception.
   * <li> 단위 test 돌릴 때에는, 아래의 if문 주석처리 하고 돌려야 작동함.
   * <p>
   *
   * @param info : id, password, ip
   * @return : LoginResponse
   */
  @PostMapping("/login/pc")
  public ResponseEntity<CommonResponse<LoginResponse>> login(@RequestBody LoginInfo loginRequest) {
    LoginInfo info = loginService.getAccountInfo(loginRequest.getLoginId());
    loginService.match(loginRequest, info);

    User user = loginService.getUser(info.getId());
    String accessToken = jwtTokenService.issueAndSaveAccessToken(user);
    log.debug("login {}", accessToken);
    LoginResponse loginResponse = new LoginResponse(user.getId(), user.getRole(), accessToken);
    log.debug("login response dto is : {}, :{}", loginResponse.getUserId(), loginResponse.getAccessToken());
    return ResponseEntity.status(HttpStatus.CREATED)
        .header(CONTENT_TYPE, APPLICATION_JSON)
        .body(CommonResponse.success(loginResponse, LOGIN_SUCCESS_MESSAGE));
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
//  @PostMapping("/login/mobile")
//  public ResponseEntity<CommonResponse<LoginResponse>> loginMobile(@RequestBody LoginInfo info)
//      throws IOException, GeoIp2Exception {
//    if (!loginService.match(info)) {
//      throw new PasswordNotMatchException(info.getId());
//    }
//
//    User user = loginService.getUser(info.getId());
//    String accessToken = jwtTokenService.generateJwtTokenFromMobile(user, info.getIp(), info.getUserAgentBrowser());
//
//    LoginResponse loginResponse = new LoginResponse(user.getId(), user.getRole(), accessToken);
//
//    log.info("login response dto is : {}, :{}", loginResponse.getUserId(),
//        loginResponse.getAccessToken());
//
//    return ResponseEntity.status(HttpStatus.CREATED)
//        .header(CONTENT_TYPE, APPLICATION_JSON)
//        .body(CommonResponse.success(loginResponse, SUCCESS));
//  }

  /**
   * <li> 토큰의 재발급 로직.
   *
   * @param regenerateAccessTokenDto : id, ip, legacyAccessToken
   * @return : String
   * @Exception : IpIsNotEqualsException
   */
  @PostMapping(value = "/login/regenerate")
  public ResponseEntity<CommonResponse<String>> regenerateAccessToken(@RequestBody
      RegenerateAccessTokenDto regenerateAccessTokenDto) throws JsonProcessingException {

    String regenerateAccessToken = jwtTokenService.regenerateAccessToken(
        regenerateAccessTokenDto.getIp(),
        regenerateAccessTokenDto.getBrowser(),
        regenerateAccessTokenDto.getLegacyAccessToken());

    return ResponseEntity.status(HttpStatus.CREATED)
        .header(CONTENT_TYPE, APPLICATION_JSON)
        .body(CommonResponse.success(regenerateAccessToken, LOGIN_SUCCESS_MESSAGE));
  }

  @PostMapping(value = "/logout")
  public ResponseEntity<CommonResponse<String>> logout(@RequestBody String accessToken) {

    accessTokenService.deleteAccessToken(accessToken);

    return ResponseEntity.status(HttpStatus.OK)
        .header(CONTENT_TYPE, APPLICATION_JSON)
        .body(CommonResponse.success(null, LOGOUT_SUCCESS_MESSAGE));
  }
}

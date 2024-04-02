package com.nhnacademy.auth.controller;

import com.nhnacademy.auth.dto.LoginInfo;
import com.nhnacademy.auth.dto.LoginResponse;
import com.nhnacademy.auth.dto.Response;
import com.nhnacademy.auth.dto.User;
import com.nhnacademy.auth.exception.PasswordNotMatchException;
import com.nhnacademy.auth.service.JwtTokenService;
import com.nhnacademy.auth.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final JwtTokenService jwtTokenService;

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";

    /**
     * 로그인에 성공했을 경우 : userId, userRole, accessToken, refreshToken 발급.
     * <p>
     * 로그인에 실패했을 경우 : exception.
     * </p>
     * @param info : gateway에서 들어오는 dataDto
     * @Exception : PasswordNotMatchException(userId)
     * @return : responseFormat
     */
    @PostMapping
    public ResponseEntity<Response<LoginResponse>> login(@RequestBody LoginInfo info) {
        if (!loginService.match(info)) {
            throw new PasswordNotMatchException(info.getId());
        }
        String accessToken = jwtTokenService.generateAccessToken(info.getId());
        String refreshToken = jwtTokenService.generateRefreshToken(info.getId());
        User user = loginService.getUser(info.getId());

        LoginResponse loginResponse = new LoginResponse(user.getUserId(), user.getUserRole(),accessToken, refreshToken);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .body(Response.success(loginResponse, "로그인 성공"));
    }

    /**
     * refreshToken이 redis에 있을 경우 : accessToken을 발급.
     * <p>
     * refreshToken이 redis에 없을 경우 : Service layer에서 exception.
     * @param refreshToken : 검색 대상자.
     * @return : responseFormat
     */
    @PostMapping(value = "/regenerate")
    public ResponseEntity<Response<String>> regenerateAccessToken(@RequestBody String refreshToken) {
        String accessToken = jwtTokenService.regenerateAccessToken(refreshToken);

        return  ResponseEntity.status(HttpStatus.CREATED)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .body(Response.success(accessToken, "regenerate access token"));
    }
}

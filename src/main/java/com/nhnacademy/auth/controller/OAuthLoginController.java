package com.nhnacademy.auth.controller;

import com.nhnacademy.auth.dto.*;
import com.nhnacademy.auth.exception.OAuthAuthorizationFailException;
import com.nhnacademy.auth.properties.PaycoOAuthProperties;
import com.nhnacademy.auth.service.JwtTokenService;
import com.nhnacademy.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@RequestMapping
@RestController
@RequiredArgsConstructor
public class OAuthLoginController {
    private final RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
    private final JwtTokenService jwtTokenService;
    ;private final PaycoOAuthProperties paycoOAuthProperties;

    /**
     * payco response_type, serviceProviderCode, userLocale url에 명시된 기본 값 입니다.
     *
     * todo: front단으로 이동
     */
    @GetMapping("/login/oauth/payco")
    public ResponseEntity<String> oauthLogin() {
        String url =  "https://id.payco.com/oauth2.0/authorize?response_type=code&serviceProviderCode=FRIENDS&userLocale=ko_KR";

        url += "&client_id=3RDwMkWpqVbBXbEs0f7uAo9" +
                "&redirect_uri=" + "http://test-vm.com:8080/login/oauth/a"; // todo: 수정하고, properties로 이동

        log.info("oauth url : {}", url);

        return ResponseEntity
                .status(HttpStatus.PERMANENT_REDIRECT)
                .location(URI.create(url))
                .build();
    }

    /**
     * payco 로그인 성공시 발급되는 code를 기반으로 access token을 발급 받고, header 값에 client id,
     * access token을 넣어 user 정보를 가지고 옵니다.
     *
     * ref - https://developers.payco.com/guide/development/apply/web
     */
    @GetMapping("/login/oauth/a")
    public ResponseEntity<CommonResponse<LoginResponse>> getUserResource(@RequestParam String code) {
        String url = "https://id.payco.com/oauth2.0/token?grant_type=authorization_code"
                + "&client_id=" + paycoOAuthProperties.getClientId()
                + "&client_secret=" + paycoOAuthProperties.getClientSecret()
                + "&code=" + code;


        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<PaycoAccessTokenResponse> res = restTemplate.exchange(url, HttpMethod.GET, entity, PaycoAccessTokenResponse.class);

        headers.add("client_id", paycoOAuthProperties.getClientId());
        headers.add("access_token", res.getBody().getAccessToken());

        ResponseEntity<PaycoUserInfoResponse> userInfo = restTemplate.exchange("https://apis-payco.krp.toastoven.net/payco/friends/find_member_v2.json", HttpMethod.POST, entity, PaycoUserInfoResponse.class);

        if (!userInfo.getBody().isSuccessful()) throw new OAuthAuthorizationFailException();

        String token = jwtTokenService.generateAccessToken(userInfo.getBody().getIdNo()); // id 값은 항상 보장되어 있는 값
        LoginResponse loginResponse = new LoginResponse(userInfo.getBody().getIdNo(), User.Role.USER, token);

        return ResponseEntity.ok().body(CommonResponse.success(loginResponse));
    }
}
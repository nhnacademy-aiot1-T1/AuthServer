package com.nhnacademy.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PaycoAccessTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;
}

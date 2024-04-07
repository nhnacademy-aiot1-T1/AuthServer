package com.nhnacademy.auth.dto;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class PaycoUserInfoResponse {
    private static final String KEY = "member";

    private HashMap<String, Object> header;
    private HashMap<String, HashMap<String, String>> data;

    public boolean isSuccessful() {
        return (boolean) header.get("isSuccessful");
    }

    public String getName() {
        return data.get(KEY).get("name");
    }

    public String getEmail() {
        return data.get(KEY).get("email");
    }

    public String getIdNo() {
        return data.get(KEY).get("idNo");
    }
}

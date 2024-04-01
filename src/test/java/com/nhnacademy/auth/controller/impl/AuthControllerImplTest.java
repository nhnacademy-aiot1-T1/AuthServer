package com.nhnacademy.auth.controller.impl;

import com.nhnacademy.auth.provider.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class AuthControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    private static final String userId = "userId";
    private static final String accessToken = "accessToken";
    private static final String refreshToken = "refreshToken";

    @Test
    void generateAccessToken() throws Exception {

        given(jwtTokenProvider.generateAccessToken(userId)).willReturn(accessToken);

        mockMvc.perform(post("/auth/accessToken")
                        .with(csrf())
                        .with(user("username").password("password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(userId))
                .andExpect(status().isCreated());
    }

    @Test
    void generateRefreshToken() throws Exception {
        given(jwtTokenProvider.generateRefreshToken(userId)).willReturn(refreshToken);

        mockMvc.perform(post("/auth/refreshToken")
                        .with(csrf())
                        .with(user("username").password("password"))
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(userId))
                .andExpect(status().isCreated());
    }

    @Test
    void regenerateAccessToken() throws Exception{
        given(jwtTokenProvider.regenerateAccessToken(userId)).willReturn(accessToken);

        mockMvc.perform(post("/auth/regenerateToken")
                        .with(csrf())
                        .with(user("username").password("password"))
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(userId))
                .andExpect(status().isCreated());
    }
}
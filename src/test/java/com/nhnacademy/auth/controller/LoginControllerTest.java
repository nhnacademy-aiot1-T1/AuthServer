//package com.nhnacademy.auth.controller;
//
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
//
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.nhnacademy.auth.adaptor.AccountAdapter;
//import com.nhnacademy.auth.dto.User;
//import com.nhnacademy.auth.dto.UserRole;
//import com.nhnacademy.auth.dto.UserStatus;
//import com.nhnacademy.auth.service.JwtTokenService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class LoginControllerTest {
//
//  @Autowired
//  private MockMvc mockMvc;
//
//  @MockBean
//  private AccountAdapter accountAdapter;
//  private JwtTokenService jwtTokenService;
//
//  private static final String userId = "user";
//  private static final String ip = "112.216.11.34";
//  private User user;
//
//  @Test
//  void login() throws Exception {
//    User user = new User(userId, UserStatus.ACTIVE, UserRole.USER);
//    given(jwtTokenService.generateAccessToken(user, ip)).willReturn("accessToken");
//
//
//    ObjectMapper objectMapper = new ObjectMapper();
//    String requestBody = objectMapper.writeValueAsString(user);
//
//    mockMvc.perform(post("/api/auth/login/pc")
//        .with(csrf())
//        .with(user("username").password("password"))
//        .contentType(MediaType.APPLICATION_JSON)
//        .content(requestBody))
//        .andExpect(status().isCreated());
//  }
//
//  @Test
//  void loginMobile() {
//  }
//
//  @Test
//  void regenerateAccessToken() {
//  }
//}
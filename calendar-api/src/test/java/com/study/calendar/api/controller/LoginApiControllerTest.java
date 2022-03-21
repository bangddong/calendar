package com.study.calendar.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.calendar.api.dto.SignUpReq;
import com.study.calendar.api.service.LoginService;
import com.study.calendar.core.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static com.study.calendar.api.service.LoginService.LOGIN_SESSION_KEY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@DisplayName("API 컨트롤러 - 로그인")
@WebMvcTest(LoginApiController.class)
class LoginApiControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper mapper;

    @MockBean
    private LoginService loginService;

    public LoginApiControllerTest(
            @Autowired MockMvc mvc,
            @Autowired ObjectMapper mapper
    ) {
        this.mvc = mvc;
        this.mapper = mapper;
    }

    @DisplayName("[API][POST] 회원가입")
    @Test
    void givenSignUpInfo_whenCreateUser_thenSuccessResponse() throws Exception {
        // Given
        SignUpReq signUpReq = createSignUpRequest(
                "qqqq",
                "qqqq@qqqq",
                "qweqwedd"
        );

        // When
        final ResultActions actions = mvc.perform(
                post("/api/sign-up")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(signUpReq))
        );

        // Then
        actions
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("[API][POST] 로그인")
    @Test
    void givenLoginInfo_whenLogin_thenSuccessResponse() throws Exception {
        // Given
        SignUpReq signUpReq = createSignUpRequest(
                "qqqq",
                "qqqq@qqqq",
                "qweqwedd"
        );

        // When
        final ResultActions actions = mvc.perform(
                post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(signUpReq))
        );

        // Then
        actions
                .andExpect(status().isOk())
                .andDo(print());
    }

    private SignUpReq createSignUpRequest(
            String name,
            String email,
            String password
    ) {
        return new SignUpReq(
                name,
                email,
                password,
                LocalDate.of(2022, 3, 21)
        );
    }

}
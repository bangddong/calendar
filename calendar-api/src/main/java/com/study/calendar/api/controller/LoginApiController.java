package com.study.calendar.api.controller;

import com.study.calendar.api.dto.LoginReq;
import com.study.calendar.api.dto.SignUpReq;
import com.study.calendar.api.service.LoginService;
import com.study.calendar.api.util.ApiUtils;
import com.study.calendar.api.util.ApiUtils.ApiDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.study.calendar.api.util.ApiUtils.success;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class LoginApiController {

    private final LoginService loginService;

    @PostMapping("/sign-up")
    public ApiDataResponse<Boolean> signUp(@Valid @RequestBody SignUpReq signUpReq, HttpSession session) {
        loginService.signUp(signUpReq, session);
        return success(true);
    }

    @PostMapping("/login")
    public ApiDataResponse<Boolean> login(@Valid @RequestBody LoginReq loginReq, HttpSession session) {
        loginService.login(loginReq, session);
        return success(true);
    }

    @PostMapping("/logout")
    public ApiDataResponse<Boolean> logout(HttpSession session) {
        loginService.logout(session);
        return success(true);
    }
}

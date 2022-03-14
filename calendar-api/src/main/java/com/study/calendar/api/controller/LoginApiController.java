package com.study.calendar.api.controller;

import com.study.calendar.api.dto.LoginReq;
import com.study.calendar.api.dto.SignUpReq;
import com.study.calendar.api.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class LoginApiController {

    private final LoginService loginService;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody SignUpReq signUpReq, HttpSession session) {
        loginService.signUp(signUpReq, session);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> signUp(@RequestBody LoginReq loginReq, HttpSession session) {
        loginService.login(loginReq, session);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        loginService.logout(session);
        return ResponseEntity.ok().build();
    }
}

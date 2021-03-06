package com.study.calendar.api.service;

import com.study.calendar.api.dto.AuthUser;
import com.study.calendar.api.dto.LoginReq;
import com.study.calendar.api.dto.SignUpReq;
import com.study.calendar.core.domain.entity.User;
import com.study.calendar.core.dto.UserCreateReq;
import com.study.calendar.core.exception.CalendarException;
import com.study.calendar.core.exception.ErrorCode;
import com.study.calendar.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    public final static String LOGIN_SESSION_KEY = "USER_ID";
    private final UserService userService;

    @Transactional
    public void signUp(SignUpReq signUpReq, HttpSession session) {
        final User user = userService.create(new UserCreateReq(
                signUpReq.getName(),
                signUpReq.getEmail(),
                signUpReq.getPassword(),
                signUpReq.getBirthday()
        ));
        session.setAttribute(LOGIN_SESSION_KEY, AuthUser.of(user.getId()));
    }

    @Transactional
    public void login(LoginReq loginReq, HttpSession session) {
        final AuthUser authUser = (AuthUser) session.getAttribute(LOGIN_SESSION_KEY);
        if (authUser != null) {
            return;
        }
        final Optional<User> user =
                userService.findPwMatchUser(loginReq.getEmail(), loginReq.getPassword());
        if (user.isPresent()) {
            session.setAttribute(LOGIN_SESSION_KEY, AuthUser.of(user.get().getId()));
        } else {
            throw new CalendarException(ErrorCode.PASSWORD_NOT_MATCH);
        }
    }

    public void logout(HttpSession session) {
        session.removeAttribute(LOGIN_SESSION_KEY);
    }

}

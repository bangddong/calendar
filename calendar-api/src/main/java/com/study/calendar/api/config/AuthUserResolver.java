package com.study.calendar.api.config;

import com.study.calendar.api.dto.AuthUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.study.calendar.api.service.LoginService.LOGIN_SESSION_KEY;

public class AuthUserResolver implements HandlerMethodArgumentResolver {

    /**
     * Method 의 파라미터가 AuthUser 타입이면 resolveArgument 를 호출한다.
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return AuthUser.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final Long userId = (Long) webRequest.getAttribute(LOGIN_SESSION_KEY, WebRequest.SCOPE_SESSION);
        if (userId == null) {
            throw new RuntimeException("bad request. no session");
        }
        return AuthUser.of(userId);
    }
}

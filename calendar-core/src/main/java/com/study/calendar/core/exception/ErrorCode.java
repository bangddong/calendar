package com.study.calendar.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    OK(0, "OK"),

    BAD_REQUEST(10000, "Bad request"),
    PASSWORD_NOT_MATCH(10001, "Password not match"),
    ALREADY_EXISTS_USER(10002, "Already exists user"),
    USER_NOT_FOUND(10003, "User not found"),
    VALIDATION_ERROR(10004, "Validation error"),
    EVENT_CREATE_OVERLAPPED_PERIOD(10005, "Event create overlapped period"),

    INTERNAL_ERROR(20000, "Internal error")
    ;

    private final Integer code;
    private final String message;

    // Custom message
    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(getMessage());
    }

    public String getMessage(Throwable e) {
        return getMessage(this.getMessage() + " - " + e.getMessage());
    }
}

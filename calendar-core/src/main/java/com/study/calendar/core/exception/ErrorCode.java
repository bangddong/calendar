package com.study.calendar.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    PASSWORD_NOT_MATCH("Password not match"),
    ALREADY_EXISTS_USER("Already exists user"),
    USER_NOT_FOUND("User not found"),
    VALIDATION_ERROR("Validation error"),
    BAD_REQUEST("Bad request"),
    EVENT_CREATE_OVERLAPPED_PERIOD("Event create overlapped period"),

    INTERNAL_ERROR("Internal error")
    ;

    private final String message;

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(getMessage());
    }

    public String getMessage(Throwable e) {
        return getMessage(this.getMessage() + " - " + e.getMessage());
    }
}

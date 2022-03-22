package com.study.calendar.core.exception;

import lombok.Getter;

@Getter
public class CalendarException extends RuntimeException{

    private final ErrorCode errorCode;

    public CalendarException() {
        super(ErrorCode.INTERNAL_ERROR.getMessage());
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

    public CalendarException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}

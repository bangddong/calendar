package com.study.calendar.api.exception;

import com.study.calendar.core.exception.CalendarException;
import com.study.calendar.core.exception.ErrorCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.study.calendar.api.util.ApiUtils.error;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleCalendarException(CalendarException ex, WebRequest request) {
        return handleExceptionInternal(
                ex, ex.getErrorCode(), request
        );
    }

    @ExceptionHandler
    public ResponseEntity<Object> validation(MethodArgumentNotValidException ex, WebRequest request) {
        return handleExceptionInternal(
                ex, ErrorCode.VALIDATION_ERROR, request
        );
    }

    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception ex, WebRequest request) {
        return handleExceptionInternal(
                ex, ErrorCode.INTERNAL_ERROR, request
        );
    }

    // 기본 handleExceptionInternal 은 BODY 가 없음.
    // ApiErrorResponse 를 내보내기 위해 오버라이드
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request
    ) {
        return handleExceptionInternal(
                ex,
                ErrorHttpStatusMapper.mapToErrorCode(status),
                headers,
                status,
                request
        );
    }

    private ResponseEntity<Object> handleExceptionInternal(
            Exception ex, ErrorCode errorCode, WebRequest request
    ) {
        return handleExceptionInternal(
                ex, errorCode, HttpHeaders.EMPTY, ErrorHttpStatusMapper.mapToStatus(errorCode), request
        );
    }

    private ResponseEntity<Object> handleExceptionInternal(
            Exception ex, ErrorCode errorCode, HttpHeaders headers, HttpStatus status, WebRequest request
    ) {
        return super.handleExceptionInternal(
                ex,
                error(false, status, errorCode.getMessage()),
                headers,
                status,
                request
        );
    }

}

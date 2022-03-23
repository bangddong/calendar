package com.study.calendar.api.exception;

import com.study.calendar.core.exception.CalendarException;
import com.study.calendar.core.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.handler.DispatcherServletWebRequest;

import java.util.Collections;

import static com.study.calendar.api.util.ApiUtils.error;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("예외 핸들러 - API 에러 처리")
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler sut;
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        sut = new GlobalExceptionHandler();
        webRequest = new DispatcherServletWebRequest(new MockHttpServletRequest());
    }

    @DisplayName("프로젝트 일반 오류 - 캘린더")
    @Test
    void givenSchedulerException_whenHandlingSchedulerException_thenReturnResponseEntity() {
        // Given
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        CalendarException e = new CalendarException(errorCode);

        // When
        ResponseEntity<Object> response = sut.handleCalendarException(e, webRequest);

        // Then
        assertThat(response)
                .hasFieldOrPropertyWithValue(
                        "body",
                        error(false, errorCode.getCode(), errorCode.getMessage())
                )
                .hasFieldOrPropertyWithValue("headers", HttpHeaders.EMPTY)
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DisplayName("발리데이션 오류")
    @Test
    void givenValidationException_whenHandlingValidationException_thenReturnResponseEntity() throws Exception {
        // Given
        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
        MethodArgumentNotValidException e = new MethodArgumentNotValidException(
                new MethodParameter(getClass().getDeclaredMethod("handle", String.class), 0),
                new MapBindingResult(Collections.emptyMap(), "name")
        );

        // When
        ResponseEntity<Object> response = sut.handleMethodArgumentNotValid(e, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, webRequest);

        // Then
        assertThat(response)
                .hasFieldOrPropertyWithValue(
                        "body",
                        error(false, errorCode.getCode(), errorCode.getMessage())
                )
                .hasFieldOrPropertyWithValue("headers", HttpHeaders.EMPTY)
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.BAD_REQUEST);
    }

    @DisplayName("기타 전체 오류")
    @Test
    void givenOtherException_whenHandlingException_thenReturnResponseEntity() throws Exception {
        // Given
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        Exception e = new Exception();

        // When
        ResponseEntity<Object> response = sut.exception(e, webRequest);

        // Then
        assertThat(response)
                .hasFieldOrPropertyWithValue(
                        "body",
                        error(false, errorCode.getCode(), errorCode.getMessage())
                )
                .hasFieldOrPropertyWithValue("headers", HttpHeaders.EMPTY)
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @SuppressWarnings("unused")
    void handle(String arg) {}

}
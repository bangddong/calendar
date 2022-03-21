package com.study.calendar.api.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 요청에 대한 공통 응답 및 에러
 */
public class ApiUtils {

    public static <T> ApiDataResponse<T> success(T response) {
        return new ApiDataResponse<>(response);
    }

    public static ApiErrorResponse error(Boolean success, Integer errorCode, String message) {
        return new ApiErrorResponse(success, errorCode, message);
    }

    @Getter
    public static class ApiDataResponse<T> extends ApiErrorResponse{
        private final T data;

        private ApiDataResponse(T data) {
            super(true, HttpStatus.OK.value(), "OK");
            this.data = data;
        }
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ApiErrorResponse {
        private final boolean success;
        private final Integer errorCode;
        private final String message;
    }

}

package com.study.calendar.api.util;

import com.study.calendar.core.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
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

    public static ApiErrorResponse error(Boolean success, Integer errorCode, Exception e) {
        return new ApiErrorResponse(success, errorCode, e.getMessage());
    }

    @Getter
    @EqualsAndHashCode(callSuper = true)
    public static class ApiDataResponse<T> extends ApiErrorResponse{
        private final T data;

        private ApiDataResponse(T data) {
            super(true, ErrorCode.OK.getCode(), ErrorCode.OK.getMessage());
            this.data = data;
        }
    }

    @Getter
    @EqualsAndHashCode
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ApiErrorResponse {
        private final boolean success;
        private final Integer errorCode;
        private final String message;
    }

}

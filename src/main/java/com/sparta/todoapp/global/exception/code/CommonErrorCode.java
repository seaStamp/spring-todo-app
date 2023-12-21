package com.sparta.todoapp.global.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 매개변수가 포함되어 있습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}

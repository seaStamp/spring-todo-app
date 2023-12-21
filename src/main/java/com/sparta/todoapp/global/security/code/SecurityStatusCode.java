package com.sparta.todoapp.global.security.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SecurityStatusCode implements StatusCode {

    SUCCESS_LOGIN(HttpStatus.OK, "로그인에 성공하였습니다."),
    FAIL_LOGIN(HttpStatus.UNAUTHORIZED, "잘못된 유저이름 또는 비밀번호입니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}

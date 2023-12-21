package com.sparta.todoapp.domain.user.exception;

import com.sparta.todoapp.global.exception.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    EXISTS_USERNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 유저이름입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}

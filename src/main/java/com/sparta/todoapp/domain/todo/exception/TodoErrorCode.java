package com.sparta.todoapp.domain.todo.exception;

import com.sparta.todoapp.global.exception.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TodoErrorCode implements ErrorCode {
    NOT_FOUND_TODO(HttpStatus.NOT_FOUND, "존재하지 않는 Todo 입니다."),
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "Todo 작성자만 수정이 가능합니다.");

    private final HttpStatus httpStatus;
    private final String message;
}

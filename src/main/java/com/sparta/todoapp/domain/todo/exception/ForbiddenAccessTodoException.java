package com.sparta.todoapp.domain.todo.exception;

import com.sparta.todoapp.global.exception.RestApiException;
import com.sparta.todoapp.global.exception.code.ErrorCode;

public class ForbiddenAccessTodoException extends RestApiException {

    public ForbiddenAccessTodoException(final ErrorCode errorCode) {
        super(errorCode);
    }
}

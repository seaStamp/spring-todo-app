package com.sparta.todoapp.domain.todo.exception;

import com.sparta.todoapp.global.exception.RestApiException;
import com.sparta.todoapp.global.exception.code.ErrorCode;

public class NotFoundTodoException extends RestApiException {

    public NotFoundTodoException(final ErrorCode errorCode) {
        super(errorCode);
    }
}

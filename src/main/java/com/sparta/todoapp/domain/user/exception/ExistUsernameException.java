package com.sparta.todoapp.domain.user.exception;

import com.sparta.todoapp.global.exception.RestApiException;
import com.sparta.todoapp.global.exception.code.ErrorCode;

public class ExistUsernameException extends RestApiException {

    public ExistUsernameException(final ErrorCode errorCode) {
        super(errorCode);
    }
}

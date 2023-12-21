package com.sparta.todoapp.domain.comment.exception;

import com.sparta.todoapp.global.exception.RestApiException;
import com.sparta.todoapp.global.exception.code.ErrorCode;

public class ForbiddenAccessCommentException extends RestApiException {

    public ForbiddenAccessCommentException(final ErrorCode errorCode) {
        super(errorCode);
    }
}

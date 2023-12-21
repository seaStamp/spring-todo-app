package com.sparta.todoapp.domain.comment.exception;

import com.sparta.todoapp.global.exception.RestApiException;
import com.sparta.todoapp.global.exception.code.ErrorCode;

public class NotFoundCommentException extends RestApiException {

    public NotFoundCommentException(final ErrorCode errorCode) {
        super(errorCode);
    }
}

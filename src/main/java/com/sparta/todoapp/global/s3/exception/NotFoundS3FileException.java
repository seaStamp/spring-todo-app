package com.sparta.todoapp.global.s3.exception;

import com.sparta.todoapp.global.exception.RestApiException;
import com.sparta.todoapp.global.exception.code.ErrorCode;

public class NotFoundS3FileException extends RestApiException {

    public NotFoundS3FileException(final ErrorCode errorCode) {
        super(errorCode);
    }
}

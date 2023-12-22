package com.sparta.todoapp.global.s3.exception;

import com.sparta.todoapp.global.exception.RestApiException;
import com.sparta.todoapp.global.exception.code.ErrorCode;

public class AwsS3InternalException extends RestApiException {

    public AwsS3InternalException(final ErrorCode errorCode) {
        super(errorCode);
    }
}

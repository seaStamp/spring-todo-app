package com.sparta.todoapp.global.s3.exception;

import com.sparta.todoapp.global.exception.RestApiException;
import com.sparta.todoapp.global.exception.code.ErrorCode;

public class FileTypeNotAllowedException extends RestApiException {

    public FileTypeNotAllowedException(final ErrorCode errorCode) {
        super(errorCode);
    }
}

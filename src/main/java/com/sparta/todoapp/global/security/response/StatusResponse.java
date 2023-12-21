package com.sparta.todoapp.global.security.response;

import com.sparta.todoapp.global.security.code.StatusCode;
import lombok.Getter;

@Getter
public class StatusResponse {

    private final int status;
    private final String code;
    private final String message;

    public StatusResponse(StatusCode statusCode) {
        this.code = statusCode.name();
        this.status = statusCode.getHttpStatus().value();
        this.message = statusCode.getMessage();
    }
}

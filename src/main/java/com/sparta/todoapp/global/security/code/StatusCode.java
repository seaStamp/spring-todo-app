package com.sparta.todoapp.global.security.code;

import org.springframework.http.HttpStatus;

public interface StatusCode {

    String name();

    HttpStatus getHttpStatus();

    String getMessage();
}

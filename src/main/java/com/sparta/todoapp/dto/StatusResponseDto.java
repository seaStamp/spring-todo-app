package com.sparta.todoapp.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class StatusResponseDto {
    private HttpStatus status;
    private String error;

    public StatusResponseDto(HttpStatus status, String error) {
        this.status = status;
        this.error = error;
    }
}

package com.sparta.todoapp.dto;

import lombok.Getter;

@Getter
public class StatusResponseDto {
    private int status;
    private String message;

    public StatusResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
    }
}

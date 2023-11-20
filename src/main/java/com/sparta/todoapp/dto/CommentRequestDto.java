package com.sparta.todoapp.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long todoId;
    private String text;
}

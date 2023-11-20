package com.sparta.todoapp.dto;

import com.sparta.todoapp.entity.Todo;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class TodoResponseDto extends StatusResponseDto{
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate;

    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.createDate = todo.getCreatedAt();
    }
}

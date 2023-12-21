package com.sparta.todoapp.domain.todo.dto.response;

import com.sparta.todoapp.domain.todo.entity.Todo;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class TodoResponseDto {
    private String title;
    private String content;
    private boolean isCompleted;
    private LocalDateTime createDate;

    public TodoResponseDto(Todo todo) {
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.isCompleted = todo.isCompleted();
        this.createDate = todo.getCreatedAt();
    }
}

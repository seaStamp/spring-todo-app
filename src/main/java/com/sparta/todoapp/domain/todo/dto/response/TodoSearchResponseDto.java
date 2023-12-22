package com.sparta.todoapp.domain.todo.dto.response;

import com.sparta.todoapp.domain.todo.entity.Todo;
import com.sparta.todoapp.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class TodoSearchResponseDto {

    private String title;
    private String content;
    private boolean isCompleted;
    private LocalDateTime createDate;
    private String username;

    public TodoSearchResponseDto(Todo todo, User user) {
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.isCompleted = todo.isCompleted();
        this.createDate = todo.getCreatedAt();
        this.username = user.getUsername();
    }
}

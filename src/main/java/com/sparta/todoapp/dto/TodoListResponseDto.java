package com.sparta.todoapp.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TodoListResponseDto {
    private UserDto user;
    private List<TodoResponseDto> todolist;
}

package com.sparta.todoapp.domain.todo.dto.response;

import com.sparta.todoapp.domain.user.dto.response.UserDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TodoListResponseDto {
    private UserDto user;
    private List<TodoResponseDto> todolist;
}

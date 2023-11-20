package com.sparta.todoapp.service;

import com.sparta.todoapp.dto.TodoRequestDto;
import com.sparta.todoapp.dto.TodoResponseDto;
import com.sparta.todoapp.entity.Todo;
import com.sparta.todoapp.entity.User;
import com.sparta.todoapp.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    public TodoResponseDto createTodo(TodoRequestDto requestDto, User user) {
        Todo todo = todoRepository.save(new Todo(requestDto, user));
        return new TodoResponseDto(todo);
    }
}

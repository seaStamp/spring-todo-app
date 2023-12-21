package com.sparta.todoapp.domain.todo.controller;

import com.sparta.todoapp.domain.todo.dto.request.TodoRequestDto;
import com.sparta.todoapp.domain.todo.dto.response.TodoListResponseDto;
import com.sparta.todoapp.domain.user.dto.response.UserDto;
import com.sparta.todoapp.domain.todo.dto.response.TodoResponseDto;
import com.sparta.todoapp.global.security.UserDetailsImpl;
import com.sparta.todoapp.domain.todo.service.TodoService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/todos")
@RestController
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto requestDto, @AuthenticationPrincipal
    UserDetailsImpl userDetails) {
        TodoResponseDto responseDto = todoService.createTodo(requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{todoId}")
    public TodoResponseDto getTodo(@PathVariable Long todoId) {
        return todoService.getTodo(todoId);
    }

    @GetMapping
    public List<TodoListResponseDto> getAllTodos() {
        List<TodoListResponseDto> response = new ArrayList<>();
        Map<UserDto, List<TodoResponseDto>> responseDtoMap = todoService.getUserTodoMap();

        responseDtoMap.forEach((key, value) -> response.add(new TodoListResponseDto(key, value)));

        return response;
    }

    @PatchMapping("/{todoId}")
    public TodoResponseDto updateTodo(@PathVariable Long todoId,
                                      @RequestBody TodoRequestDto requestDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return todoService.updateTodo(todoId, requestDto, userDetails.getUser());
    }

    @PatchMapping("/{todoId}/complete")
    public TodoResponseDto completeTodo(@PathVariable Long todoId,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return todoService.completeTodo(todoId, userDetails.getUser());
    }
}

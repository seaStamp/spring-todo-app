package com.sparta.todoapp.domain.todo.controller;

import com.sparta.todoapp.domain.model.dto.PageRequestDto;
import com.sparta.todoapp.domain.todo.dto.request.TodoRequestDto;
import com.sparta.todoapp.domain.todo.dto.response.TodoListResponseDto;
import com.sparta.todoapp.domain.todo.dto.response.TodoResponseDto;
import com.sparta.todoapp.domain.todo.dto.response.TodoSearchResponseDto;
import com.sparta.todoapp.domain.todo.service.impl.TodoServiceImpl;
import com.sparta.todoapp.domain.user.dto.response.UserDto;
import com.sparta.todoapp.global.security.UserDetailsImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/todos")
@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoServiceImpl todoService;

    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        TodoResponseDto responseDto = todoService.createTodo(requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<TodoResponseDto> getTodo(@PathVariable Long todoId) {
        TodoResponseDto responseDto = todoService.getTodo(todoId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<TodoListResponseDto>> getAllTodos() {
        List<TodoListResponseDto> response = new ArrayList<>();
        Map<UserDto, List<TodoResponseDto>> responseDtoMap = todoService.getUserTodoMap();

        responseDtoMap.forEach((key, value) -> response.add(new TodoListResponseDto(key, value)));

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{todoId}")
    public ResponseEntity<TodoResponseDto> updateTodo(@PathVariable Long todoId,
        @RequestBody TodoRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        TodoResponseDto responseDto = todoService.updateTodo(todoId, requestDto,
            userDetails.getUser());
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{todoId}/complete")
    public ResponseEntity<TodoResponseDto> completeTodo(@PathVariable Long todoId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        TodoResponseDto responseDto = todoService.completeTodo(todoId, userDetails.getUser());
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TodoSearchResponseDto>> searchTodos(
        @RequestParam(value = "keyword") String keyword,
        @RequestBody PageRequestDto pageRequestDto) {
        Page<TodoSearchResponseDto> responseDto = todoService.searchTodos(keyword, pageRequestDto);
        return ResponseEntity.ok(responseDto);
    }
}

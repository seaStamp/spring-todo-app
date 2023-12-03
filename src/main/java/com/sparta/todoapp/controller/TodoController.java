package com.sparta.todoapp.controller;

import com.sparta.todoapp.dto.StatusResponseDto;
import com.sparta.todoapp.dto.TodoListResponseDto;
import com.sparta.todoapp.dto.TodoRequestDto;
import com.sparta.todoapp.dto.TodoResponseDto;
import com.sparta.todoapp.dto.UserDto;
import com.sparta.todoapp.security.UserDetailsImpl;
import com.sparta.todoapp.service.TodoService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;
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
    public ResponseEntity<StatusResponseDto> getTodo(@PathVariable Long todoId) {
        try {
            TodoResponseDto responseDto = todoService.getTodo(todoId);
            return ResponseEntity.ok().body(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new StatusResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<TodoListResponseDto>> getAllTodos() {
        List<TodoListResponseDto> response = new ArrayList<>();
        Map<UserDto, List<TodoResponseDto>> responseDtoMap = todoService.getUserTodoMap();

        responseDtoMap.forEach((key, value) -> response.add(new TodoListResponseDto(key, value)));

        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/{todoId}")
    public ResponseEntity<StatusResponseDto> updateTodo(@PathVariable Long todoId,
                                                        @RequestBody TodoRequestDto requestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            TodoResponseDto responseDto = todoService.updateTodo(todoId, requestDto, userDetails.getUser());
            return ResponseEntity.ok().body(responseDto);
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException | RejectedExecutionException e) {
            return ResponseEntity.badRequest()
                    .body(new StatusResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @PatchMapping("/{todoId}/complete")
    public ResponseEntity<StatusResponseDto> completeTodo(@PathVariable Long todoId,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            TodoResponseDto responseDto = todoService.completeTodo(todoId, userDetails.getUser());
            return ResponseEntity.ok().body(responseDto);
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException | RejectedExecutionException e) {
            return ResponseEntity.badRequest()
                    .body(new StatusResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }
}

package com.sparta.todoapp.controller;

import com.sparta.todoapp.dto.TodoRequestDto;
import com.sparta.todoapp.dto.TodoResponseDto;
import com.sparta.todoapp.security.UserDetailsImpl;
import com.sparta.todoapp.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
        System.out.println("사용자 " + userDetails.getUsername());
        TodoResponseDto responseDto = todoService.createTodo(requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}

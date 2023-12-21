package com.sparta.todoapp.domain.user.controller;

import com.sparta.todoapp.domain.user.dto.request.SignupRequestDto;
import com.sparta.todoapp.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);

        return ResponseEntity.noContent().build();
    }
}

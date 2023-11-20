package com.sparta.todoapp.controller;

import com.sparta.todoapp.dto.SignupRequestDto;
import com.sparta.todoapp.dto.StatusResponseDto;
import com.sparta.todoapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
    public ResponseEntity<Object> signup(@Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            StringBuilder message = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                message.append(fieldError.getDefaultMessage()).append("\n");
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(new StatusResponseDto(HttpStatus.BAD_REQUEST.value(), message.toString()));
        }
        try {
            userService.signup(requestDto);
            String successMessage = "가입에 성공했습니다";
            return ResponseEntity.ok().body(new StatusResponseDto(HttpStatus.OK.value(),successMessage));
        } catch (IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(new StatusResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        }
    }
}

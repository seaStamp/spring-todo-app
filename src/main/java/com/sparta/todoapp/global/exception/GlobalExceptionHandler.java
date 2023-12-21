package com.sparta.todoapp.global.exception;

import java.util.concurrent.RejectedExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<StatusResponseDto> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        StatusResponseDto statusResponseDto = new StatusResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(statusResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<StatusResponseDto> notFoundExceptionHandler(NotFoundException e) {
        StatusResponseDto statusResponseDto = new StatusResponseDto(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(statusResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({RejectedExecutionException.class})
    public ResponseEntity<StatusResponseDto> rejectedExecutionExceptionHandler(RejectedExecutionException e) {
        StatusResponseDto statusResponseDto = new StatusResponseDto(e.getMessage(), HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(statusResponseDto, HttpStatus.FORBIDDEN);
    }
}

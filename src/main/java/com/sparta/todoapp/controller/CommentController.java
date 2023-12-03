package com.sparta.todoapp.controller;

import com.sparta.todoapp.dto.CommentRequestDto;
import com.sparta.todoapp.dto.CommentResponseDto;
import com.sparta.todoapp.dto.StatusResponseDto;
import com.sparta.todoapp.security.UserDetailsImpl;
import com.sparta.todoapp.service.CommentService;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/todos/{todoId}/comments")
    public ResponseEntity<?> createComment(@PathVariable Long todoId,
                                           @RequestBody CommentRequestDto requestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            CommentResponseDto responseDto = commentService.createComment(todoId, requestDto, userDetails.getUser());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new StatusResponseDto(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @GetMapping("/todos/{todoId}/comments")
    public ResponseEntity<?> getComments(@PathVariable Long todoId) {
        try {
            List<CommentResponseDto> responseDto = commentService.getComments(todoId);
            return ResponseEntity.ok().body(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new StatusResponseDto(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<?> modifyComment(@PathVariable Long commentId,
                                           @RequestBody CommentRequestDto requestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            CommentResponseDto responseDto = commentService.modifyComment(commentId, requestDto, userDetails.getUser());
            return ResponseEntity.ok().body(responseDto);
        } catch (RejectedExecutionException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new StatusResponseDto(HttpStatus.FORBIDDEN.value(), e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new StatusResponseDto(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<StatusResponseDto> deleteComment(@PathVariable Long commentId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            commentService.deleteComment(commentId, userDetails.getUser());
            return ResponseEntity.ok().body(new StatusResponseDto(HttpStatus.OK.value(), "댓글 삭제 성공"));
        } catch (RejectedExecutionException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new StatusResponseDto(HttpStatus.FORBIDDEN.value(), e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new StatusResponseDto(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }
}

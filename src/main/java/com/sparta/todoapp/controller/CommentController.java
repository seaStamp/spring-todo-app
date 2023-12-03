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
    public CommentResponseDto createComment(@PathVariable Long todoId,
                                            @RequestBody CommentRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(todoId, requestDto, userDetails.getUser());
    }

    @GetMapping("/todos/{todoId}/comments")
    public List<CommentResponseDto> getComments(@PathVariable Long todoId) {
        return commentService.getComments(todoId);
    }

    @PatchMapping("/comments/{commentId}")
    public CommentResponseDto modifyComment(@PathVariable Long commentId,
                                            @RequestBody CommentRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.modifyComment(commentId, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<StatusResponseDto> deleteComment(@PathVariable Long commentId,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(commentId, userDetails.getUser());
        return ResponseEntity.ok().body(new StatusResponseDto("댓글 삭제 성공", HttpStatus.OK.value()));
    }
}

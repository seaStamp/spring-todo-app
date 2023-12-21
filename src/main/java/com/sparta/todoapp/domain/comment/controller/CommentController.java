package com.sparta.todoapp.domain.comment.controller;

import com.sparta.todoapp.domain.comment.dto.request.CommentRequestDto;
import com.sparta.todoapp.domain.comment.dto.response.CommentResponseDto;
import com.sparta.todoapp.domain.comment.service.CommentService;
import com.sparta.todoapp.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(commentId, userDetails.getUser());
        return ResponseEntity.ok().build();
    }
}

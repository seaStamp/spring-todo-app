package com.sparta.todoapp.domain.comment.dto.response;

import com.sparta.todoapp.domain.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private String text;
    private String username;
    private LocalDateTime createDate;

    public CommentResponseDto(Comment comment){
        this.text = comment.getText();
        this.username = comment.getUser().getUsername();
        this.createDate = comment.getCreatedAt();
    }
}

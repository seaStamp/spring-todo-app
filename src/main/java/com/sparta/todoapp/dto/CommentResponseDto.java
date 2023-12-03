package com.sparta.todoapp.dto;

import com.sparta.todoapp.entity.Comment;
import com.sparta.todoapp.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private String text;
    private String username;
    private LocalDateTime createDate;

    public CommentResponseDto(Comment comment, User user){
        this.text = comment.getText();
        this.username = user.getUsername();
        this.createDate = comment.getCreatedAt();
    }
}

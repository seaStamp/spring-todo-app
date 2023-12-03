package com.sparta.todoapp.dto;

import com.sparta.todoapp.entity.Comment;
import com.sparta.todoapp.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private String text;
    private UserDto user;
    private LocalDateTime createDate;

    public CommentResponseDto(Comment comment, User user){
        this.text = comment.getText();
        this.user = new UserDto(user);
        this.createDate = comment.getCreatedAt();
    }
}

package com.sparta.todoapp.dto;

import com.sparta.todoapp.entity.Comment;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private Long id;
    private String text;
    private UserDto user;
    private LocalDateTime createDate;

    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.text = comment.getText();
        this.user = new UserDto(comment.getUser());
        this.createDate = comment.getCreatedAt();
    }
}

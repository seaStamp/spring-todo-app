package com.sparta.todoapp.dto;

import com.sparta.todoapp.entity.User;
import lombok.Getter;

@Getter
public class UserDto {
    private String username;

    public UserDto(User user){
        this.username = user.getUsername();
    }
}

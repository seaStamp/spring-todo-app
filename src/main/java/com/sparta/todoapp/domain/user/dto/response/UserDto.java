package com.sparta.todoapp.domain.user.dto.response;

import com.sparta.todoapp.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserDto {
    private String username;

    public UserDto(User user){
        this.username = user.getUsername();
    }
}

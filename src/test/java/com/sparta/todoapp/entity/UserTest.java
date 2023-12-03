package com.sparta.todoapp.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {
    @Test
    @DisplayName("User 생성시 전달인자와 같은 값을 가진다")
    public void createUser_Success() {
        // given
        String username = "testUser";
        String password = "testPassword";

        // when
        User user = User.builder()
                .username(username)
                .password(password)
                .build();

        // then
        assertEquals(user.getUsername(),username);
        assertEquals(user.getUsername(),username);
    }
}
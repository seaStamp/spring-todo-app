package com.sparta.todoapp.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.sparta.todoapp.entity.User;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("유저명으로 유저 찾기 - 성공")
    void findByUsernameSuccessTest() {
        // given
        User testUser = new User("testUser", "password");
        userRepository.save(testUser);

        // when
        Optional<User> foundUser = userRepository.findByUsername("testUser");

        // then
        assertTrue(foundUser.isPresent());
        assertEquals(testUser, foundUser.get());
    }

    @Test
    @DisplayName("유저명으로 유저 찾기 - 실패")
    void findByUsernameFailureTest() {
        // given
        User testUser = User.builder()
                .username("testUser")
                .password("password")
                .build();
        userRepository.save(testUser);

        // when
        Optional<User> foundUser = userRepository.findByUsername("nonExistingUser");

        // then
        assertTrue(foundUser.isEmpty());
    }
}
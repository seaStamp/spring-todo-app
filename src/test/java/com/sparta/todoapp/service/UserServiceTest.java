package com.sparta.todoapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.sparta.todoapp.dto.SignupRequestDto;
import com.sparta.todoapp.entity.User;
import com.sparta.todoapp.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("회원가입 성공 테스트")
    void signupSuccessTest() {
        // given
        SignupRequestDto requestDto = new SignupRequestDto("testuser", "pas1234");
        given(userRepository.findByUsername(requestDto.getUsername())).willReturn(Optional.empty());
        given(passwordEncoder.encode(requestDto.getPassword())).willReturn(
                new BCryptPasswordEncoder().encode(requestDto.getPassword()));

        // when
        userService.signup(requestDto);

        // then
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("회원가입 실패 테스트 : 중복된 사용자")
    public void signupFailTest() {

        //Given
        User user = new User();
        SignupRequestDto requestDto = new SignupRequestDto("testuser", "pas1234");
        given(userRepository.findByUsername(requestDto.getUsername())).willReturn(Optional.of(user));
        given(passwordEncoder.encode(requestDto.getPassword())).willReturn("encodedPassword");

        //When
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.signup(requestDto));

        //Then
        verify(userRepository, never()).save(any()); // 저장되지 않음
        assertEquals("중복된 사용자가 있습니다.", exception.getMessage());
    }
}
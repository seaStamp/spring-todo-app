package com.sparta.todoapp.service;

import com.sparta.todoapp.dto.SignupRequestDto;
import com.sparta.todoapp.entity.User;
import com.sparta.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode((requestDto.getPassword()));

        // DB에 중복된 username이 있는지 확인
        Optional<User> usernameCheck = userRepository.findByUsername(username);
        if (usernameCheck.isPresent()){
            throw new IllegalArgumentException("중복된 사용자가 있습니다.");
        }

        // 회원 저장
        User user = new User(username,password);
        userRepository.save(user);
    }
}

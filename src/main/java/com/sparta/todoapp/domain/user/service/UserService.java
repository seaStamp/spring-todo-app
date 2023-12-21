package com.sparta.todoapp.domain.user.service;

import com.sparta.todoapp.domain.user.dto.request.SignupRequestDto;
import com.sparta.todoapp.domain.user.entity.User;
import com.sparta.todoapp.domain.user.exception.ExistUsernameException;
import com.sparta.todoapp.domain.user.exception.UserErrorCode;
import com.sparta.todoapp.domain.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
        // TODO : Exit로 수정
        Optional<User> usernameCheck = userRepository.findByUsername(username);
        if (usernameCheck.isPresent()) {
            throw new ExistUsernameException(UserErrorCode.EXISTS_USERNAME);
        }

        // 회원 저장
        User user = new User(username, password);
        userRepository.save(user);
    }
}

package com.sparta.todoapp.domain.user.service;

import com.sparta.todoapp.domain.user.dto.request.SignupRequestDto;

public interface UserService {

    /**
     * 회원가입
     *
     * @param requestDto 회원가입 요청 정보
     */
    void signup(SignupRequestDto requestDto);
}

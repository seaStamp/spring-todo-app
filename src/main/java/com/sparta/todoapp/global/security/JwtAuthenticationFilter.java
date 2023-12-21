package com.sparta.todoapp.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.todoapp.global.jwt.JwtUtil;
import com.sparta.todoapp.domain.user.dto.request.LoginRequestDto;
import com.sparta.todoapp.global.exception.StatusResponseDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final ObjectMapper ob = new ObjectMapper();
    private final static String loginSuccessMessage = "로그인에 성공했습니다.";
    private final static String notFoundUserMessage = "회원을 찾을 수 없습니다.";

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e){
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();

        String token = jwtUtil.createToken(username);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        response.setStatus(HttpServletResponse.SC_OK);

        // JSON 형식으로 응답 메시지, 상태코드 생성
        String jsonResponse = ob.writeValueAsString(new StatusResponseDto(loginSuccessMessage, response.getStatus()));

        PrintWriter writer = response.getWriter();
        writer.println(jsonResponse);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        // JSON 형식으로 응답 메시지, 상태코드 생성
        String jsonResponse = ob.writeValueAsString(new StatusResponseDto(notFoundUserMessage, response.getStatus()));

        PrintWriter writer = response.getWriter();
        writer.println(jsonResponse);
    }
}
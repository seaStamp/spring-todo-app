package com.sparta.todoapp.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.todoapp.domain.user.controller.UserController;
import com.sparta.todoapp.domain.user.dto.request.SignupRequestDto;
import com.sparta.todoapp.domain.user.service.impl.UserServiceImpl;
import com.sparta.todoapp.global.config.MockSpringSecurityFilter;
import com.sparta.todoapp.global.config.WebSecurityConfig;
import com.sparta.todoapp.global.jwt.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = {UserController.class}, excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfig.class)})
public class UserControllerTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    MockMvc mvc;

    //@Mock
    @MockBean
    UserServiceImpl userService;

    //@Mock
    @MockBean
    JwtUtil jwtUtil;


    @BeforeEach
    public void mockUserSetup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity(new MockSpringSecurityFilter())).build();

    }


    @Test
    @DisplayName("회원가입")
    void signupTest() throws Exception {

        //given
        SignupRequestDto requestDto = new SignupRequestDto("testuser", "123456789");

        //when
        String body = mapper.writeValueAsString(requestDto);

        //then
        mvc.perform(post("/api/users/signup")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent()).andDo(print());
    }

}

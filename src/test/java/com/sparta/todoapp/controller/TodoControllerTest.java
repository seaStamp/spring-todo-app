package com.sparta.todoapp.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.todoapp.domain.model.dto.PageRequestDto;
import com.sparta.todoapp.domain.todo.controller.TodoController;
import com.sparta.todoapp.domain.todo.dto.request.TodoRequestDto;
import com.sparta.todoapp.domain.todo.service.impl.TodoServiceImpl;
import com.sparta.todoapp.domain.user.entity.User;
import com.sparta.todoapp.global.config.MockSpringSecurityFilter;
import com.sparta.todoapp.global.config.WebSecurityConfig;
import com.sparta.todoapp.global.security.UserDetailsImpl;
import java.security.Principal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(
    controllers = {TodoController.class},
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfig.class)
    }
)
class TodoControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    MockMvc mvc;

    @MockBean
    TodoServiceImpl todoService;

    @Autowired
    ObjectMapper objectMapper;

    private Principal mockPrincipal;

    @BeforeEach
    void setup() {
        User testUser = User.builder()
            .username("userA")
            .password("12345678")
            .build();
        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(
            testUserDetails, "", testUserDetails.getAuthorities()
        );

        mvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity(new MockSpringSecurityFilter()))
            .build();
    }

    @Test
    @DisplayName("Todo 생성 테스트")
    void createTodoTest() throws Exception {
        // given
        String title = "할 일 제목";
        String content = "할 일 내용";
        TodoRequestDto requestDto = new TodoRequestDto(title, content);

        // when - then
        String body = objectMapper.writeValueAsString(requestDto);

        mvc.perform(post("/api/todos")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Todo 수정 테스트")
    void updateTodoTest() throws Exception {
        // given
        Long todoId = 1L;
        String title = "수정 제목";
        String content = "수정 내용";
        TodoRequestDto requestDto = new TodoRequestDto(title, content);

        // when - then
        String body = objectMapper.writeValueAsString(requestDto);

        mvc.perform(patch("/api/todos/{todoId}", todoId)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal)
            )
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Todo 검색 조회 테스트")
    void serchTodoTest() throws Exception {
        // given
        int page = 1;
        int size = 3;
        PageRequestDto requestDto = new PageRequestDto(page, size);

        // when - then
        String body = objectMapper.writeValueAsString(requestDto);

        mvc.perform(get("/api/todos/search")
                .param("keyword", "일")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal)
            )
            .andExpect(status().isOk());
    }
}

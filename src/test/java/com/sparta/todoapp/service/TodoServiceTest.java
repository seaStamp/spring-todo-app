package com.sparta.todoapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sparta.todoapp.dto.TodoRequestDto;
import com.sparta.todoapp.dto.TodoResponseDto;
import com.sparta.todoapp.entity.Todo;
import com.sparta.todoapp.entity.User;
import com.sparta.todoapp.repository.TodoRepository;
import com.sparta.todoapp.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {
    @InjectMocks
    private TodoService todoService;

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private UserRepository userRepository;
    private User testUser;
    @BeforeEach
    void setUp() {
        testUser = new User("testUser", "password");
    }

    @Test
    @DisplayName("Todo 생성 테스트")
    void createTodoTest() {
        // given
        TodoRequestDto requestDto = new TodoRequestDto("테스트 제목", "테스트 내용");
        Todo todo = new Todo(requestDto, testUser);

        given(todoRepository.save(any())).willReturn(todo);

        // when
        TodoResponseDto result = todoService.createTodo(requestDto, testUser);

        // then
        assertEquals(result.getTitle(),requestDto.getTitle());
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    @DisplayName("todo 단건 조회 테스트 : 성공")
    void getTodoSuccessTest() {
        // given
        Long existingTodoId = 1L;
        Todo existingTodo = new Todo(new TodoRequestDto("테스트 제목", "테스트 유저"), testUser);
        when(todoRepository.findById(existingTodoId)).thenReturn(Optional.of(existingTodo));

        // when
        TodoResponseDto responseDto = todoService.getTodo(existingTodoId);

        // then
        assertNotNull(responseDto);
        assertEquals(existingTodo.getTitle(), responseDto.getTitle());
        assertEquals(existingTodo.getContent(), responseDto.getContent());
    }

    @Test
    @DisplayName("todo 단건 조회 테스트 : 실패")
    void getTodoFailTest() {
        // given
        Long existingTodoId = 1L;
        Todo existingTodo = new Todo(new TodoRequestDto("Title", "Content"), testUser);
        when(todoRepository.findById(existingTodoId)).thenReturn(Optional.of(existingTodo));

        // when
        TodoResponseDto responseDto = todoService.getTodo(existingTodoId);

        // then
        assertNotNull(responseDto);
        assertEquals(existingTodo.getTitle(), responseDto.getTitle());
        assertEquals(existingTodo.getContent(), responseDto.getContent());
    }
}
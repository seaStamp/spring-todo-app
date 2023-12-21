package com.sparta.todoapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.sparta.todoapp.domain.todo.dto.request.TodoRequestDto;
import com.sparta.todoapp.domain.todo.dto.response.TodoResponseDto;
import com.sparta.todoapp.domain.todo.entity.Todo;
import com.sparta.todoapp.domain.todo.exception.ForbiddenAccessTodoException;
import com.sparta.todoapp.domain.todo.exception.NotFoundTodoException;
import com.sparta.todoapp.domain.todo.repository.TodoRepository;
import com.sparta.todoapp.domain.todo.service.TodoService;
import com.sparta.todoapp.domain.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @InjectMocks
    private TodoService todoService;

    @Mock
    private TodoRepository todoRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("testUser", "password");
        testUser.setId(123L);
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
        assertEquals(result.getTitle(), requestDto.getTitle());
        assertEquals(result.getContent(), requestDto.getContent());
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Nested
    @DisplayName("Todo 단건 조회 테스트")
    class GetTodoTest {

        @Test
        @DisplayName("성공 - 존재하는 todoId")
        void getTodoSuccessTest() {
            // given
            Long existingTodoId = 1L;
            Todo existingTodo = new Todo(new TodoRequestDto("테스트 제목", "테스트 유저"), testUser);
            given(todoRepository.findById(existingTodoId)).willReturn(Optional.of(existingTodo));

            // when
            TodoResponseDto responseDto = todoService.getTodo(existingTodoId);

            // then
            assertNotNull(responseDto);
            assertEquals(existingTodo.getTitle(), responseDto.getTitle());
            assertEquals(existingTodo.getContent(), responseDto.getContent());
        }

        @Test
        @DisplayName("실패 - 존재하는 todoId")
        void getTodoFailTest() {
            // given
            Long nonExistingTodoId = 1L;
            given(todoRepository.findById(nonExistingTodoId)).willReturn(Optional.empty());

            // when - then
            Exception exception = assertThrows(NotFoundTodoException.class,
                () -> todoService.getTodo(nonExistingTodoId));
        }
    }

    @Nested
    @DisplayName("Todo 유효성 테스트")
    class ValidateTodo {

        @Test
        @DisplayName("성공 - 수정하려는 Todo가 자신의 할일 Todo일때")
        void validateTodoSuccessTest() {
            // given
            Long existingTodoId = 4L;
            List<Todo> todoList = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                TodoRequestDto requestDto = new TodoRequestDto("할일 제목 " + i, "할일 내용 " + i);
                Todo todo = new Todo(requestDto, testUser);
                todo.setId((long) i + 1);
                todoList.add(todo);
            }
            given(todoRepository.findById(existingTodoId)).willReturn(Optional.of(todoList.get(3)));

            // when
            Todo result = todoService.validateTodo(existingTodoId, testUser);

            // then
            assertEquals("할일 제목 3", result.getTitle());
        }

        @Test
        @DisplayName("실패 - 수정하려는 Todo가 존재하지 않음")
        void validateTodoFailTest_NotFoundTodo() {
            // given
            Long nonExistingTodoId = 4L; //
            List<Todo> todoList = new ArrayList<>();

            // when-then
            Exception exception = assertThrows(NotFoundTodoException.class,
                () -> todoService.validateTodo(nonExistingTodoId, testUser));
        }

        @Test
        @DisplayName("실패 - 수정하려는 Todo의 작성자가 아닐 때")
        void validateTodoFailTest_NotAuthor() {
            // given
            User otherUser = new User("otherUser", "password");
            otherUser.setId(12L);
            Long existingTodoId = 1L; //
            List<Todo> todoList = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                TodoRequestDto requestDto = new TodoRequestDto("할일 제목 " + i, "할일 내용 " + i);
                Todo todo = new Todo(requestDto, testUser);
                todo.setId((long) i + 1);
                todoList.add(todo);
            }

            given(todoRepository.findById(existingTodoId)).willReturn(Optional.of(todoList.get(0)));

            // when - then
            Exception exception = assertThrows(ForbiddenAccessTodoException.class,
                () -> todoService.validateTodo(existingTodoId, otherUser));
        }
    }
}
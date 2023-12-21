package com.sparta.todoapp.model;

import static org.junit.jupiter.api.Assertions.*;

import com.sparta.todoapp.domain.todo.dto.request.TodoRequestDto;
import com.sparta.todoapp.domain.todo.entity.Todo;
import com.sparta.todoapp.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TodoTest {
    User user;

    @BeforeEach
    void setUp() {
        user = Mockito.mock(User.class);
        Mockito.when(user.getUsername()).thenReturn("testUser");
        Mockito.when(user.getPassword()).thenReturn("password");
    }

    @Test
    @DisplayName("Todo 생성시 전달인자와 같은 값인지 테스트 ")
    void createTodoTest() {

        // given
        String title = "테스트 제목";
        String content = "테스트 내용";
        final TodoRequestDto requestDto = new TodoRequestDto(title, content);

        // when
        Todo todo = new Todo(requestDto, user);

        // then
        assertEquals(todo.getTitle(), title);
        assertEquals(todo.getContent(), content);
        assertFalse(todo.isCompleted()); // 생성 되었을 때 기본으로 false 처리한다.
        assertEquals(todo.getUser().getUsername(), user.getUsername());
        assertEquals(todo.getUser().getPassword(), user.getPassword());
    }

    @Test
    @DisplayName("Todo 완료 메서드 테스트")
    void completeCheckTest(){
        // given
        Todo todo = new Todo();

        // when
        todo.complete();

        // then
        assertTrue(todo.isCompleted());
    }

    @Test
    @DisplayName("Todo 제목&내용 수정 테스트")
    void updateTodoBothTest(){
        // given
        Todo todo = Todo.builder()
                .requestDto(new TodoRequestDto("테스트 제목", "테스트 내용"))
                .user(user)
                .build();
        TodoRequestDto modifyRequestDto = new TodoRequestDto("수정한 제목" , "수정한 내용");

        // when
        todo.update(modifyRequestDto);

        // then
        assertEquals(todo.getTitle(), modifyRequestDto.getTitle());
        assertEquals(todo.getContent(), modifyRequestDto.getContent());
    }

    @Test
    @DisplayName("Todo 제목 수정 테스트")
    void updateTodoTitleTest(){
        // given
        Todo todo = Todo.builder()
                .requestDto(new TodoRequestDto("테스트 제목", "테스트 내용"))
                .user(user)
                .build();
        TodoRequestDto modifyRequestDto = new TodoRequestDto("수정한 제목" , null);

        // when
        todo.update(modifyRequestDto);

        // then
        assertEquals(todo.getTitle(), modifyRequestDto.getTitle());
        assertNotEquals(todo.getContent(), modifyRequestDto.getContent());
    }

    @Test
    @DisplayName("Todo 내용 수정 테스트")
    void updateTodoContentTest(){
        // given
        Todo todo = Todo.builder()
                .requestDto(new TodoRequestDto("테스트 제목", "테스트 내용"))
                .user(user)
                .build();
        TodoRequestDto modifyRequestDto = new TodoRequestDto(null , "수정한 내용");

        // when
        todo.update(modifyRequestDto);

        // then
        assertNotEquals(todo.getTitle(), modifyRequestDto.getTitle());
        assertEquals(todo.getContent(), modifyRequestDto.getContent());
    }
}
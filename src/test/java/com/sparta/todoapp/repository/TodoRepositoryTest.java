package com.sparta.todoapp.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sparta.todoapp.config.TestConfig;
import com.sparta.todoapp.domain.todo.dto.request.TodoRequestDto;
import com.sparta.todoapp.domain.todo.entity.Todo;
import com.sparta.todoapp.domain.todo.repository.TodoRepository;
import com.sparta.todoapp.domain.user.entity.User;
import com.sparta.todoapp.domain.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@Import(TestConfig.class)
@ActiveProfiles("test")
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("testUser", "password");
        testUser = userRepository.save(testUser);
    }

    @Test
    @DisplayName("Todo db에 저장하기")
    void saveTodoTest() {
        // given
        TodoRequestDto requestDto = new TodoRequestDto("할일 제목 ", "할일 내용 ");
        Todo newTodo = Todo.builder()
            .requestDto(requestDto)
            .user(testUser)
            .build();

        // when
        Todo saveTodo = todoRepository.save(newTodo);

        // then
        assertEquals(requestDto.getTitle(), saveTodo.getTitle());
        assertEquals(requestDto.getContent(), saveTodo.getContent());
    }

    @Nested
    @DisplayName("Todo Id로 가져오기")
    class FindById {

        @Test
        @DisplayName("성공")
        void FindByIdSuccess() {
            // given
            TodoRequestDto requestDto = new TodoRequestDto("할일 제목 ", "할일 내용 ");
            Todo todo = Todo.builder()
                .requestDto(requestDto)
                .user(testUser)
                .build();
            todoRepository.save(todo);

            // when
            Optional<Todo> foundTodo = todoRepository.findById(todo.getId());

            // then
            assertTrue(foundTodo.isPresent());
            assertEquals(todo.getId(), foundTodo.get().getId());
        }

        @Test
        @DisplayName("실패")
        void FindByIdFail() {
            // given
            User otherUser = new User("otherUser", "password");
            userRepository.save(otherUser);
            Long wrongTodoId = 100L;

            TodoRequestDto requestDto = new TodoRequestDto("할일 제목 ", "할일 내용 ");
            Todo todo = Todo.builder()
                .requestDto(requestDto)
                .user(testUser)
                .build();
            todoRepository.save(todo);

            // when
            Optional<Todo> foundTodo = todoRepository.findById(wrongTodoId);

            // then
            assertFalse(foundTodo.isPresent());
        }
    }


    @Nested
    @DisplayName("특정 사용자의 할일 늦게 생성된 순으로 가져오기")
    class FindAllByUserAndIsCompletedOrderByCreatedAtDescTest {

        @Test
        @DisplayName("완료되지 않은 할일 가져오기")
        void FindIsCompletedFalse() {
            // given
            List<Todo> todoList = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                TodoRequestDto requestDto = new TodoRequestDto("할일 제목 " + i, "할일 내용 " + i);
                Todo todo = new Todo(requestDto, testUser);
                todo.setCreatedAt(LocalDateTime.now());
                todoList.add(todoRepository.save(todo));
            }
            todoList.get(2).setCompleted(true); // 할일 완료 처리

            // when
            List<Todo> incompleteTodos = todoRepository.findAllByUserAndIsCompleted(testUser,
                false);

            // then
            assertEquals(2, incompleteTodos.size());
            assertEquals("할일 제목 1", incompleteTodos.get(0).getTitle());
            assertEquals("할일 제목 0", incompleteTodos.get(1).getTitle());
        }

        @Test
        @DisplayName("완료된 할일 가져오기")
        void FindAllIsCompletedTrue() {
            // given
            List<Todo> todoList = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                TodoRequestDto requestDto = new TodoRequestDto("할일 제목 " + i, "할일 내용 " + i);
                Todo todo = new Todo(requestDto, testUser);
                todo.setCreatedAt(LocalDateTime.now());
                todoList.add(todoRepository.save(todo));
            }
            todoList.get(2).setCompleted(true); // 할일 완료 처리
            todoList.get(0).setCompleted(true);

            // when
            List<Todo> completeTodos = todoRepository.findAllByUserAndIsCompleted(testUser, true);

            // then
            assertEquals(2, completeTodos.size());
            assertEquals("할일 제목 2", completeTodos.get(0).getTitle());
            assertEquals("할일 제목 0", completeTodos.get(1).getTitle());
        }

        @Test
        @DisplayName("게시물 전체 페이징 조회 테스트")
        void seachByContainsTitleOrContentTest() {
            Todo todo1 = new Todo(new TodoRequestDto("제목1", "내용1"), testUser);
            Todo todo2 = new Todo(new TodoRequestDto("제목2", "내용2"), testUser);
            Todo todo3 = new Todo(new TodoRequestDto("다른 제목", "다른 내용"), testUser);
            todoRepository.saveAll(List.of(todo1, todo2, todo3));

            String keyword = "다른";

            Pageable pageable = PageRequest.of(0, 5);

            // when
            Page<Todo> result = todoRepository.searchByContainsTitleOrContent(keyword, pageable);

            // then
            assertThat(result).isNotNull();
            assertThat(result.getContent()).hasSize(1);
            assertThat(result.getContent().get(0).getTitle()).contains(keyword);
        }
    }
}
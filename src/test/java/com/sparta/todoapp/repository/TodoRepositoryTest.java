package com.sparta.todoapp.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.sparta.todoapp.dto.TodoRequestDto;
import com.sparta.todoapp.entity.Todo;
import com.sparta.todoapp.entity.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
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

//    @Nested
//    @DisplayName("특정 사용자의 할일 늦게 생성된 순으로 가져오기")
//    class FindAllByUserAndIsCompletedOrderByCreatedAtDescTest {
        @Test
        @DisplayName("완료되지 않은 할일 가져오기")
        void FindIsCompletedFalse() {
            // given
            List<Todo> todoList = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                TodoRequestDto requestDto = new TodoRequestDto("할일 제목 " + i, "할일 내용 " + i);
                Todo todo = new Todo(requestDto, testUser);
                todo.setId((long) i + 1);
                todo.setCreatedAt(LocalDateTime.now());
                todoList.add(todoRepository.save(todo));
            }
            todoList.get(2).setCompleted(true); // 할일 완료 처리

            // when
            List<Todo> incompleteTodos = todoRepository.findAllByUserAndIsCompletedOrderByCreatedAtDesc(testUser,
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
                todo.setId((long) i + 1);
                todo.setCreatedAt(LocalDateTime.now());
                todoList.add(todoRepository.save(todo));
            }
            todoList.get(2).setCompleted(true); // 할일 완료 처리
            todoList.get(0).setCompleted(true);

            // when
            List<Todo> completeTodos = todoRepository.findAllByUserAndIsCompletedOrderByCreatedAtDesc(testUser, true);

            // then
            assertEquals(2, completeTodos.size());
            assertEquals("할일 제목 2", completeTodos.get(0).getTitle());
            assertEquals("할일 제목 0", completeTodos.get(1).getTitle());
        }
//    }
}
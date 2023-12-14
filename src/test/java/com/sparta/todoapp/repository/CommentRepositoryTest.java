package com.sparta.todoapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sparta.todoapp.dto.CommentRequestDto;
import com.sparta.todoapp.dto.TodoRequestDto;
import com.sparta.todoapp.entity.Comment;
import com.sparta.todoapp.entity.Todo;
import com.sparta.todoapp.entity.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;
    private Todo testTodo;

    @BeforeEach
    void setUp() {
        testUser = new User("testUser", "password");
        testUser = userRepository.save(testUser);
        TodoRequestDto requestDto = new TodoRequestDto("할일 제목", "할일내용");
        testTodo = todoRepository.save(new Todo(requestDto, testUser));
    }

    @Nested
    @DisplayName("특정 Todo의 늦게 생성된 순으로 댓글가져오기")
    class FindAllByTodoIdOrderByCreatedAtTest {
        @Test
        @DisplayName("가져오기 성공")
        void FindSuccess() {
            // given
            List<Comment> comments = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                CommentRequestDto requestDto = new CommentRequestDto();
                requestDto.setText("댓글 내용 " + i);
                Comment comment = new Comment(requestDto, testUser, testTodo);
                comment.setCreatedAt(LocalDateTime.now());
                comments.add(commentRepository.save(comment));
            }

            // when
            List<Comment> foundComments = commentRepository.findAllByTodoIdOrderByCreatedAt(testTodo.getId());

            // then
            assertEquals(3, foundComments.size());
            assertEquals("댓글 내용 0", foundComments.get(0).getText());
            assertEquals("댓글 내용 1", foundComments.get(1).getText());
            assertEquals("댓글 내용 2", foundComments.get(2).getText());
        }
    }
}
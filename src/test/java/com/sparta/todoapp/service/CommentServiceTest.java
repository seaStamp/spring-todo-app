package com.sparta.todoapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.sparta.todoapp.dto.CommentRequestDto;
import com.sparta.todoapp.dto.CommentResponseDto;
import com.sparta.todoapp.dto.TodoRequestDto;
import com.sparta.todoapp.dto.TodoResponseDto;
import com.sparta.todoapp.entity.Comment;
import com.sparta.todoapp.entity.Todo;
import com.sparta.todoapp.entity.User;
import com.sparta.todoapp.exception.NotFoundException;
import com.sparta.todoapp.repository.CommentRepository;
import com.sparta.todoapp.repository.TodoRepository;
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
class CommentServiceTest {
    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TodoRepository todoRepository;
    private User testUser;
    private Todo testTodo;

    @BeforeEach
    void setUp() {
        testUser = new User("testUser", "password");
        testUser.setId(123L);
        TodoRequestDto requestDto = new TodoRequestDto("테스트 제목", "테스트 내용");
        testTodo = new Todo(requestDto, testUser);
        testTodo.setId(12L);
    }

    @Nested
    @DisplayName("Comment 생성 테스트")
    class CreateComment {
        @Test
        @DisplayName("성공 - 유효한 TodoId")
        void createCommentSuccessTest() {
            // given
            Long existingTodoId = 12L;
            CommentRequestDto requestDto = new CommentRequestDto("댓글 생성");
            Comment comment = new Comment(requestDto, testUser, testTodo);
            given(todoRepository.findById(existingTodoId)).willReturn(Optional.of(testTodo));
            given(commentRepository.save(any())).willReturn(comment);

            // when
            CommentResponseDto result = commentService.createComment(existingTodoId, requestDto, testUser);

            // then
            assertEquals(requestDto.getText(), result.getText());
            verify(todoRepository, times(1)).findById(any());
            verify(commentRepository, times(1)).save(any(Comment.class));
        }

        @Test
        @DisplayName("실패 - 존재하지 않는 TodoId")
        void createCommentFailTest() {
            // given
            Long existingTodoId = 12L;
            CommentRequestDto requestDto = new CommentRequestDto("댓글 생성");
            Comment comment = new Comment(requestDto, testUser, testTodo);
            given(todoRepository.findById(existingTodoId)).willReturn(Optional.empty());

            // when
            Exception exception = assertThrows(NotFoundException.class,
                    () -> commentService.createComment(existingTodoId, requestDto, testUser));

            // then
            assertEquals("존재하지 않는 todo 입니다.", exception.getMessage());
            verify(todoRepository, times(1)).findById(any());
            verify(commentRepository, never()).save(any(Comment.class));
        }
    }
}
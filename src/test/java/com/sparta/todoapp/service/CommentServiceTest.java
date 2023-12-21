package com.sparta.todoapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.sparta.todoapp.domain.comment.dto.request.CommentRequestDto;
import com.sparta.todoapp.domain.comment.dto.response.CommentResponseDto;
import com.sparta.todoapp.domain.comment.entity.Comment;
import com.sparta.todoapp.domain.comment.exception.ForbiddenAccessCommentException;
import com.sparta.todoapp.domain.comment.exception.NotFoundCommentException;
import com.sparta.todoapp.domain.comment.repository.CommentRepository;
import com.sparta.todoapp.domain.comment.service.impl.CommentServiceImpl;
import com.sparta.todoapp.domain.todo.dto.request.TodoRequestDto;
import com.sparta.todoapp.domain.todo.entity.Todo;
import com.sparta.todoapp.domain.todo.exception.NotFoundTodoException;
import com.sparta.todoapp.domain.todo.repository.TodoRepository;
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
class CommentServiceTest {

    @InjectMocks
    private CommentServiceImpl commentService;

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
            CommentResponseDto result = commentService.createComment(existingTodoId, requestDto,
                testUser);

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
            Exception exception = assertThrows(NotFoundTodoException.class,
                () -> commentService.createComment(existingTodoId, requestDto, testUser));

            // then
            verify(todoRepository, times(1)).findById(any());
            verify(commentRepository, never()).save(any(Comment.class));
        }
    }

    @Nested
    @DisplayName("Comment 유효성 테스트")
    class FindComment {

        @Test
        @DisplayName("성공 - 수정/삭제 하려는 Comment가 존재하고, 자신의 Comment일때")
        void findCommentSuccessTest() {
            // given
            Long existingCommentId = 4L;
            List<Comment> commentList = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                CommentRequestDto requestDto = new CommentRequestDto("댓글 내용 " + i);
                Comment comment = new Comment(requestDto, testUser, testTodo);
                comment.setId((long) i + 1);
                commentList.add(comment);
            }
            given(commentRepository.findById(existingCommentId)).willReturn(
                Optional.of(commentList.get(3)));

            // when
            Comment result = commentService.findComment(existingCommentId, testUser);

            // then
            assertEquals("댓글 내용 3", result.getText());
        }

        @Test
        @DisplayName("실패 - 수정/삭제 하려는 Comment가 존재하지 않음")
        void findCommentFailTest_NotFoundComment() {
            // given
            Long nonExistingCommentId = 4L; //
            List<Comment> commentList = new ArrayList<>();

            // when - then
            Exception exception = assertThrows(NotFoundCommentException.class,
                () -> commentService.findComment(nonExistingCommentId, testUser));
        }

        @Test
        @DisplayName("실패 - 수정/삭제 하려는 Comment의 작성자가 아닐 때")
        void findCommentFailTest_NotAuthor() {
            // given
            User otherUser = new User("otherUser", "password");
            otherUser.setId(12L);
            Long existingCommentId = 1L; //
            List<Comment> commentList = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                CommentRequestDto requestDto = new CommentRequestDto("댓글 내용 " + i);
                Comment comment = new Comment(requestDto, testUser, testTodo);
                comment.setId((long) i + 1);
                commentList.add(comment);
            }

            given(commentRepository.findById(existingCommentId)).willReturn(
                Optional.of(commentList.get(0)));

            // when - then
            Exception exception = assertThrows(ForbiddenAccessCommentException.class,
                () -> commentService.findComment(existingCommentId, otherUser));
        }
    }
}
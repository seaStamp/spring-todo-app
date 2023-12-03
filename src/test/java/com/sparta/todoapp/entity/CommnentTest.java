package com.sparta.todoapp.entity;

import static org.junit.jupiter.api.Assertions.*;

import com.sparta.todoapp.dto.CommentRequestDto;
import com.sparta.todoapp.dto.TodoRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CommnentTest {

    Todo todo;
    User user;

    void setUp() {
        user = Mockito.spy(User.builder()
                .username("testUser")
                .password("password")
                .build());

        todo = Mockito.spy(
                Todo.builder()
                        .requestDto(new TodoRequestDto("테스트 제목", "테스트 내용"))
                        .build());
    }

    @Test
    @DisplayName("Comment 생성시 전달인자와 같은 값을 가진다")
    void testCommentConstructor() {
        // given
        String text = "테스트 댓글";

        // when
        Comment comment = Comment.builder()
                .requestDto(new CommentRequestDto(text))
                .user(user)
                .todo(todo)
                .build();

        // then
        assertEquals(comment.getText(), text);
        assertEquals(comment.getUser(), user);
        assertEquals(comment.getTodo(), todo);
    }

    @Test
    @DisplayName("Comment의 내용 수정 테스트")
    void updateComment() {
        // given
        Comment comment = Comment.builder()
                .requestDto(new CommentRequestDto("테스트 댓글"))
                .user(user)
                .todo(todo)
                .build();
        CommentRequestDto modifyRequestDto = new CommentRequestDto("수정한 댓글");


        // when
        comment.update(modifyRequestDto);

        // then
        assertEquals(comment.getText(),modifyRequestDto.getText());
    }
}
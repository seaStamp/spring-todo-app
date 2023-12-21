package com.sparta.todoapp.domain.comment.service.impl;

import com.sparta.todoapp.domain.comment.dto.request.CommentRequestDto;
import com.sparta.todoapp.domain.comment.dto.response.CommentResponseDto;
import com.sparta.todoapp.domain.comment.entity.Comment;
import com.sparta.todoapp.domain.comment.exception.CommentErrorCode;
import com.sparta.todoapp.domain.comment.exception.ForbiddenAccessCommentException;
import com.sparta.todoapp.domain.comment.exception.NotFoundCommentException;
import com.sparta.todoapp.domain.comment.repository.CommentRepository;
import com.sparta.todoapp.domain.comment.service.CommentService;
import com.sparta.todoapp.domain.todo.entity.Todo;
import com.sparta.todoapp.domain.todo.exception.NotFoundTodoException;
import com.sparta.todoapp.domain.todo.exception.TodoErrorCode;
import com.sparta.todoapp.domain.todo.repository.TodoRepository;
import com.sparta.todoapp.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;

    @Override
    public CommentResponseDto createComment(Long todoId, CommentRequestDto requestDto, User user) {
        // 해당 Todo가 있는지 확인
        Todo todo = todoRepository.findById(todoId)
            .orElseThrow(() -> new NotFoundTodoException(TodoErrorCode.NOT_FOUND_TODO));
        Comment comment = new Comment(requestDto, user, todo);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Override
    @Transactional(readOnly = false)
    public List<CommentResponseDto> getComments(Long todoId) {
        // 해당 Todo가 있는지 확인
        todoRepository.findById(todoId)
            .orElseThrow(() -> new NotFoundTodoException(TodoErrorCode.NOT_FOUND_TODO));

        List<Comment> commentList = commentRepository.findAllByTodoIdOrderByCreatedAt(todoId);
        return commentList.stream().map(CommentResponseDto::new).toList();
    }

    @Override
    public CommentResponseDto modifyComment(Long commentId, CommentRequestDto requestDto,
        User user) {
        // comment가 있는지 확인 + 작성자가 맞는지 확인
        Comment comment = findComment(commentId, user);

        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }

    @Override
    public void deleteComment(Long commentId, User user) {
        // comment가 있는지 확인 + 작성자가 맞는지 확인
        Comment comment = findComment(commentId, user);

        commentRepository.delete(comment);
    }

    @Override
    public Comment findComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new NotFoundCommentException(CommentErrorCode.NOT_FOUND_COMMENT));
        if (!user.getId().equals(comment.getUser().getId())) {
            throw new ForbiddenAccessCommentException(CommentErrorCode.FORBIDDEN_ACCESS);
        }
        return comment;
    }
}

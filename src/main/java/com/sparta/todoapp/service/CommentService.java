package com.sparta.todoapp.service;

import com.sparta.todoapp.dto.CommentRequestDto;
import com.sparta.todoapp.dto.CommentResponseDto;
import com.sparta.todoapp.entity.Comment;
import com.sparta.todoapp.entity.Todo;
import com.sparta.todoapp.entity.User;
import com.sparta.todoapp.exception.NotFoundException;
import com.sparta.todoapp.repository.CommentRepository;
import com.sparta.todoapp.repository.TodoRepository;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;

    public CommentResponseDto createComment(Long todoId, CommentRequestDto requestDto, User user) {
        // 해당 Todo가 있는지 확인
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 todo 입니다."));
        Comment comment = new Comment(requestDto, user, todo);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Transactional(readOnly = false)
    public List<CommentResponseDto> getComments(Long todoId) {
        // 해당 Todo가 있는지 확인
        todoRepository.findById(todoId).orElseThrow(() -> new NotFoundException("존재하지 않는 todo 입니다."));

        List<Comment> commentList = commentRepository.findAllByTodoIdOrderByCreatedAt(todoId);
        return commentList.stream().map(CommentResponseDto::new).toList();
    }

    public CommentResponseDto modifyComment(Long commentId, CommentRequestDto requestDto, User user) {
        // comment가 있는지 확인 + 작성자가 맞는지 확인
        Comment comment = findComment(commentId, user);

        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }

    public void deleteComment(Long commentId, User user) {
        // comment가 있는지 확인 + 작성자가 맞는지 확인
        Comment comment = findComment(commentId, user);

        commentRepository.delete(comment);
    }

    public Comment findComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 댓글 입니다."));
        if(!user.getId().equals(comment.getUser().getId())){
            throw new RejectedExecutionException("댓글 작성자만 삭제 or 수정이 가능 합니다.");
        }
        return comment;
    }
}

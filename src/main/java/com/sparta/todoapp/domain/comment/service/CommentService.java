package com.sparta.todoapp.domain.comment.service;

import com.sparta.todoapp.domain.comment.dto.request.CommentRequestDto;
import com.sparta.todoapp.domain.comment.dto.response.CommentResponseDto;
import com.sparta.todoapp.domain.comment.entity.Comment;
import com.sparta.todoapp.domain.user.entity.User;
import java.util.List;

public interface CommentService {

    /**
     * 댓글 생성
     *
     * @param todoId     댓글을 작성할 Todo게시글
     * @param requestDto 댓글 생성 요청 정보
     * @param user       댓글 생성 요청자
     * @return 댓글 생성 결과
     */
    CommentResponseDto createComment(Long todoId, CommentRequestDto requestDto, User user);

    /**
     * 댓글 조회
     *
     * @param todoId 조회할 Todo게시글
     * @return 조회된 댓글 목록
     */
    List<CommentResponseDto> getComments(Long todoId);

    /**
     * 댓글 수정
     *
     * @param commentId  수정할 댓글
     * @param requestDto 댓글 수정 요청정보
     * @param user       댓글 수정 요청자
     * @return 댓글 수정 결과
     */
    CommentResponseDto modifyComment(Long commentId, CommentRequestDto requestDto,
        User user);


    /**
     * 댓글 삭제
     *
     * @param commentId 삭제할 댓글
     * @param user      댓글 삭제 요청자
     */
    void deleteComment(Long commentId, User user);

    /**
     * 댓글 찾기(유효성 검사)
     *
     * @param commentId 찾을 댓글 ID
     * @param user      찾을 댓글의 작성자
     * @return 찾은 댓글 반환
     */
    Comment findComment(Long commentId, User user);
}

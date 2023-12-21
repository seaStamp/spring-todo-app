package com.sparta.todoapp.domain.comment.exception;

import com.sparta.todoapp.global.exception.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode {

    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "존재하지 않는 댓글 입니다."),
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "댓글 작성자만 삭제 or 수정이 가능 합니다.");

    private final HttpStatus httpStatus;
    private final String message;
}

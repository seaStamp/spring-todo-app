package com.sparta.todoapp.domain.todo.service;

import com.sparta.todoapp.domain.todo.dto.request.TodoRequestDto;
import com.sparta.todoapp.domain.todo.dto.response.TodoResponseDto;
import com.sparta.todoapp.domain.todo.entity.Todo;
import com.sparta.todoapp.domain.user.dto.response.UserDto;
import com.sparta.todoapp.domain.user.entity.User;
import java.util.List;
import java.util.Map;

public interface TodoService {

    /**
     * Todo게시글 생성
     *
     * @param requestDto Todo게시글 생성 요청정보
     * @param user       Todo게시글 작성자
     * @return Todo게시글 생성 결과
     */
    TodoResponseDto createTodo(TodoRequestDto requestDto, User user);

    /**
     * Todo게시글 단건조회
     *
     * @param todoId 조회할 게시글
     * @return 조회결과
     */
    TodoResponseDto getTodo(Long todoId);

    /**
     * Todo게시글 전체조회
     *
     * @return 전체 조회 결과
     */
    Map<UserDto, List<TodoResponseDto>> getUserTodoMap();


    List<TodoResponseDto> convertTodoListToResponseDtoList(List<Todo> todoList);

    /**
     * Todo게시글 수정
     *
     * @param todoId     수정할 게시글 id
     * @param requestDto 수정할 정보
     * @param user       수정 요청자
     * @return 수정한 결과
     */
    TodoResponseDto updateTodo(Long todoId, TodoRequestDto requestDto, User user);

    /**
     * Todo 완료처리
     *
     * @param todoId 완료처리할 게시글 id
     * @param user   완료처리 요청자
     * @return
     */
    TodoResponseDto completeTodo(Long todoId, User user);

    /**
     * Todo게시글 유효성 검사
     *
     * @param todoId 찾을 todo게시글
     * @param user   찾을 게시글의 작성자
     * @return 찾은 Todo게시글 반환
     */
    Todo validateTodo(Long todoId, User user);
}

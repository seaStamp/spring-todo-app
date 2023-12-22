package com.sparta.todoapp.domain.todo.service.impl;

import com.sparta.todoapp.domain.model.dto.PageRequestDto;
import com.sparta.todoapp.domain.todo.dto.request.TodoRequestDto;
import com.sparta.todoapp.domain.todo.dto.response.TodoResponseDto;
import com.sparta.todoapp.domain.todo.dto.response.TodoSearchResponseDto;
import com.sparta.todoapp.domain.todo.entity.Todo;
import com.sparta.todoapp.domain.todo.exception.ForbiddenAccessTodoException;
import com.sparta.todoapp.domain.todo.exception.NotFoundTodoException;
import com.sparta.todoapp.domain.todo.exception.TodoErrorCode;
import com.sparta.todoapp.domain.todo.repository.TodoRepository;
import com.sparta.todoapp.domain.todo.service.TodoService;
import com.sparta.todoapp.domain.user.dto.response.UserDto;
import com.sparta.todoapp.domain.user.entity.User;
import com.sparta.todoapp.domain.user.repository.UserRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Override
    public TodoResponseDto createTodo(TodoRequestDto requestDto, User user) {
        Todo todo = todoRepository.save(new Todo(requestDto, user));
        return new TodoResponseDto(todo);
    }

    @Override
    @Transactional(readOnly = true)
    public TodoResponseDto getTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
            .orElseThrow(() -> new NotFoundTodoException(TodoErrorCode.NOT_FOUND_TODO));
        return new TodoResponseDto(todo);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<UserDto, List<TodoResponseDto>> getUserTodoMap() {
        Map<UserDto, List<TodoResponseDto>> userTodoMap = new HashMap<>();

        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            UserDto userDto = new UserDto(user);
            // 사용자의 할일목록을 내림차순으로 가져옴
            List<TodoResponseDto> todolistDto = convertTodoListToResponseDtoList(
                todoRepository.findAllByUserAndIsCompleted(user, false));
            todolistDto.addAll(convertTodoListToResponseDtoList(
                todoRepository.findAllByUserAndIsCompleted(user, true)));
            userTodoMap.put(userDto, todolistDto);
        }
        return userTodoMap;
    }

    @Override
    public List<TodoResponseDto> convertTodoListToResponseDtoList(List<Todo> todoList) {
        return todoList.stream()
            .map(TodoResponseDto::new)
            .collect(Collectors.toList());
    }

    @Override
    public TodoResponseDto updateTodo(Long todoId, TodoRequestDto requestDto, User user) {
        Todo todo = validateTodo(todoId, user);
        todo.update(requestDto);
        return new TodoResponseDto(todo);
    }

    @Override
    public TodoResponseDto completeTodo(Long todoId, User user) {
        Todo todo = validateTodo(todoId, user);
        todo.complete(); // 완료처리
        return new TodoResponseDto(todo);
    }

    @Override
    public Todo validateTodo(Long todoId, User user) {
        Todo todo;

        // 해당하는 todo가 존재하는지 확인
        todo = todoRepository.findById(todoId).orElseThrow(() ->
            new NotFoundTodoException(TodoErrorCode.NOT_FOUND_TODO));

        // 작성자가 맞는지 확인
        if (!(todo.getUser().getId().equals(user.getId()))) {
            throw new ForbiddenAccessTodoException(TodoErrorCode.FORBIDDEN_ACCESS);
        }

        return todo;
    }

    @Override
    public Page<TodoSearchResponseDto> searchTodos(
        final String keyword, final PageRequestDto pageRequestDto) {
        Page<Todo> todoList = todoRepository.searchByUserAndContainsTitleOrContent(keyword,
            pageRequestDto.toPageable());
        return todoList.map(todo -> new TodoSearchResponseDto(todo, todo.getUser()));
    }
}

package com.sparta.todoapp.service;

import com.sparta.todoapp.dto.TodoRequestDto;
import com.sparta.todoapp.dto.TodoResponseDto;
import com.sparta.todoapp.dto.UserDto;
import com.sparta.todoapp.entity.Todo;
import com.sparta.todoapp.entity.User;
import com.sparta.todoapp.repository.TodoRepository;
import com.sparta.todoapp.repository.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoResponseDto createTodo(TodoRequestDto requestDto, User user) {
        Todo todo = todoRepository.save(new Todo(requestDto, user));
        return new TodoResponseDto(todo);
    }

    public TodoResponseDto getTodo(Long todoId) {
        System.out.println("조회중");
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 할일 ID 입니다"));
        System.out.println("조회 끝");
        return new TodoResponseDto(todo);
    }

    public Map<UserDto, List<TodoResponseDto>> getUserTodoMap() {
        Map<UserDto, List<TodoResponseDto>> userTodoMap = new HashMap<>();

        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            UserDto userDto = new UserDto(user);
            // 사용자의 할일목록을 내림차순으로 가져옴
            List<TodoResponseDto> todolistDto = convertTodoListToResponseDtoList(
                    todoRepository.findAllByUserOrderByCreatedAtDesc(user));
            userTodoMap.put(userDto, todolistDto);
        }
        return userTodoMap;
    }

    public List<TodoResponseDto> convertTodoListToResponseDtoList(List<Todo> todoList) {
        return todoList.stream()
                .map(TodoResponseDto::new)
                .collect(Collectors.toList());
    }
}

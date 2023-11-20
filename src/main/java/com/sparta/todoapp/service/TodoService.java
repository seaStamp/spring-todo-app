package com.sparta.todoapp.service;

import com.sparta.todoapp.dto.TodoRequestDto;
import com.sparta.todoapp.dto.TodoResponseDto;
import com.sparta.todoapp.dto.UserDto;
import com.sparta.todoapp.entity.Todo;
import com.sparta.todoapp.entity.User;
import com.sparta.todoapp.repository.TodoRepository;
import com.sparta.todoapp.repository.UserRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 할일 ID 입니다"));
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

    @Transactional
    public TodoResponseDto updateTodo(Long todoId, TodoRequestDto requestDto, User user) {
        Todo todo = validateTodo(todoId, user);
        todo.update(requestDto);
        return new TodoResponseDto(todo);
    }

    @Transactional
    public TodoResponseDto completeTodo(Long todoId, User user) {
        Todo todo = validateTodo(todoId, user);
        todo.complete(); // 완료처리
        return new TodoResponseDto(todo);
    }



    public Todo validateTodo(Long todoid, User user) {
        Todo todo;

        // 아예 작성하지 않았을 경우
        if(todoRepository.findAll().isEmpty()){
            throw new NullPointerException("작성한 Todo가 없습니다.");
        }

        // 해당하는 todo가 존재하는지 확인
         todo = todoRepository.findById(todoid).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 할일 ID입니다."));

        // 작성자가 맞는지 확인
        if (!(todo.getUser().getId().equals(user.getId()))){
            throw new RejectedExecutionException("작성자만 수정이 가능합니다.");
        }

        return todo;
    }
}

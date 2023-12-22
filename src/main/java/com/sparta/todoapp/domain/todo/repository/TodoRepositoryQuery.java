package com.sparta.todoapp.domain.todo.repository;

import com.sparta.todoapp.domain.todo.entity.Todo;
import com.sparta.todoapp.domain.user.entity.User;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepositoryQuery {

    List<Todo> findAllByUserAndIsCompleted(User user, boolean b);

    List<Todo> searchByUserAndContainsTitleOrContent(String keyword, String username);
}

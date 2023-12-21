package com.sparta.todoapp.domain.todo.repository;

import com.sparta.todoapp.domain.todo.entity.Todo;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepositoryQuery {

    List<Todo> searchByUserAndContainsTitleOrMember(String keyword, String username);
}

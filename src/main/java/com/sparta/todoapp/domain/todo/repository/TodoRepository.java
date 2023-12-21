package com.sparta.todoapp.domain.todo.repository;

import com.sparta.todoapp.domain.todo.entity.Todo;
import com.sparta.todoapp.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByUserAndIsCompletedOrderByCreatedAtDesc(User user, boolean b);
}

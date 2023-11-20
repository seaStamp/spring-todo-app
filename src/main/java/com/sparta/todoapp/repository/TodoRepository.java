package com.sparta.todoapp.repository;

import com.sparta.todoapp.entity.Todo;
import com.sparta.todoapp.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByUserOrderByCreatedAtDesc(User user);
}

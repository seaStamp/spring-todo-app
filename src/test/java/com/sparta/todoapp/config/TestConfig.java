package com.sparta.todoapp.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.todoapp.domain.todo.repository.TodoRepositoryQuery;
import com.sparta.todoapp.domain.todo.repository.TodoRepositoryQueryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public TodoRepositoryQuery todoRepositoryQuery() {
        return new TodoRepositoryQueryImpl(jpaQueryFactory());
    }
}

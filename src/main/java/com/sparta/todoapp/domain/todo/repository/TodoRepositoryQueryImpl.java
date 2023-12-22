package com.sparta.todoapp.domain.todo.repository;

import static com.sparta.todoapp.domain.todo.entity.QTodo.todo;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.todoapp.domain.todo.entity.Todo;
import com.sparta.todoapp.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryQueryImpl implements TodoRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Todo> findAllByUserAndIsCompleted(final User user, final boolean b) {
        JPAQuery<Todo> query = jpaQueryFactory.select(todo)
            .from(todo)
            .leftJoin(todo.user).fetchJoin()
            .where(todo.isCompleted.eq(b), todo.user.eq(user))
            .orderBy(todo.createdAt.desc());

        return query.fetch();
    }

    @Override
    public List<Todo> searchByUserAndContainsTitleOrContent(final String keyword,
        final String username) {

        JPAQuery<Todo> query = jpaQueryFactory.select(todo)
            .from(todo)
            .leftJoin(todo.user).fetchJoin()
            .where(searchTitle(keyword).or(searchContent(keyword)),
                usernameEquals(username)
            )
            .orderBy(todo.createdAt.desc());

        return query.fetch();
    }

    private BooleanExpression searchTitle(String keyword) {
        return keyword != null ? todo.title.contains(keyword) : null;
    }

    private BooleanExpression searchContent(String keyword) {
        return keyword != null ? todo.content.contains(keyword) : null;
    }

    private BooleanExpression usernameEquals(String username) {
        return username != null ? todo.user.username.eq(username) : null;
    }
}

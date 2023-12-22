package com.sparta.todoapp.domain.todo.entity;

import com.sparta.todoapp.domain.model.Timestamped;
import com.sparta.todoapp.domain.todo.dto.request.TodoRequestDto;
import com.sparta.todoapp.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "todos")
@NoArgsConstructor
public class Todo extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean isCompleted;

    @Column
    private String imageName;

    @Column
    private String imagePath;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Todo(TodoRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.user = user;
        this.isCompleted = false;
    }

    public void update(TodoRequestDto requestDto) {
        if (requestDto.getTitle() != null) {
            System.out.println("get title");
            this.title = requestDto.getTitle();
        }
        if (requestDto.getContent() != null) {
            System.out.println("get content");
            this.content = requestDto.getContent();
        }
    }

    public void uploadImage(final String imageName, final String imagePath) {
        this.imageName = imageName;
        this.imagePath = imagePath;
    }

    public void complete() {
        this.isCompleted = true;
    }
}

package com.sparta.todoapp.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@AllArgsConstructor
public class PageRequestDto {

    private int page;
    private int size;

    public Pageable toPageable() {
        return PageRequest.of(page - 1, size);
    }
}

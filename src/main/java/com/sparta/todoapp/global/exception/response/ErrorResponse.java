package com.sparta.todoapp.global.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.validation.FieldError;

@Getter
@Builder
@RequiredArgsConstructor
public class ErrorResponse {

    private final int status;
    private final String code;
    private final String message;

    @JsonInclude(Include.NON_EMPTY)
    private final List<ValidationError> errors;

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class ValidationError {

        private final String field;
        private final String message;

        public static ValidationError of(final FieldError fieldError) {
            return ValidationError.builder()
                .field(fieldError.getField())
                .message(fieldError.getDefaultMessage())
                .build();
        }
    }
}

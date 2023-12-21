package com.sparta.todoapp.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.sparta.todoapp.domain.user.dto.request.SignupRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SignupRequestDtoTest {

    @Nested
    @DisplayName("유저 요청 DTO 생성")
    class createSignupRequestDto {
        @Test
        @DisplayName("유저 요청 DTO생성 성공")
        void createSignupRequestDto_success() {
            // given
            SignupRequestDto signupRequestDto = new SignupRequestDto();
            signupRequestDto.setUsername("user123");
            signupRequestDto.setPassword("12345678");

            // when
            Set<ConstraintViolation<SignupRequestDto>> violations = validate(signupRequestDto);

            // then
            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("유저 요청 DTO생성 실패")
        void createSignupRequestDto_fail_wrongUserName() {
            // Given
            SignupRequestDto signupRequestDto = new SignupRequestDto();
            signupRequestDto.setUsername("Invalid user name");
            signupRequestDto.setPassword("12345678");

            // When
            Set<ConstraintViolation<SignupRequestDto>> violations = validate(signupRequestDto);

            // Then
        }
    }

    private Set<ConstraintViolation<SignupRequestDto>> validate(SignupRequestDto signupRequestDto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(signupRequestDto);
    }
}
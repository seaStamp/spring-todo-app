package com.sparta.todoapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank
    @Size(min = 4, max = 10, message = "4-10글자로 username을 설정해주세요")
    @Pattern(regexp = "^[a-z0-9]+$", message = "소문자와 숫자를 포함한 username을 입력해주세요.")
    private String username;

    @NotBlank
    @Size(min = 8, max = 15, message = "8-15글자로 password를 설정해주세요")
    @Pattern(regexp = "^[a-zA-z0-9]+$", message = "대소문자와 숫자를 포함한 password를 입력해주세요.")
    private String password;

}
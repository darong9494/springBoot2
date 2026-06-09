package com.tenco.csr_blog_v1.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequest {
    public record UpdateDTO(
            @Email(message = "이메일 형식이 올바르지 않습니다.")
            String email,
            @NotBlank(message = "비밀번호를 입력해주세요")
            @Size(min = 4, max = 60, message = "비밀번호는 4~20자 이하입니다.")
            String password) {}
}

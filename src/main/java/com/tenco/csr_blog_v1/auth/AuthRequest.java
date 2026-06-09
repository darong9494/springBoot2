package com.tenco.csr_blog_v1.auth;

import com.tenco.csr_blog_v1.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class AuthRequest {
    public record JoinDTO(
            @Size(min = 4, max = 20, message = "유저 이름은 최소 4자이상 20자 이하여야 합니다.")
            @NotEmpty(message = "유저이름을 입력해주세요.")
            String username,
            @Size(min = 4, max = 60, message = "비밀번호는 4~60자여야 합니다.")
            @NotBlank(message = "비밀번호를 입력해주세요.")
            String password,
            @Email(message = "이메일 형식이 올바르지 않습니다.")
            String email) {

        public User toEntity(String encPassword) {
            return User.builder()
                    .username(username)
                    .password(encPassword)
                    .email(email)
                    .build();
        }
    }

    public record LoginDTO(@NotEmpty(message = "유저이름을 입력하세요.") String username,
                           @NotBlank(message = "비밀번호를 입력해주세요") String password) {
    }
}

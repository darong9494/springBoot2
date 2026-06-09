package com.tenco.csr_blog_v1.auth;

import com.tenco.csr_blog_v1.user.User;

import java.util.List;

public class AuthResponse {
    // 회원가입 후 내려줄 데이터
    public record DTO(Integer id, String username, String email, List<String> roles) {
        // 사용자 정의 생성자 (User user)
        public DTO(User user) {
            this(user.getId(), user.getUsername(), user.getEmail(), user.getRoles());
        }
    }

    // JWT 토큰에 사용자 정보가 들어가있다.
    public record TokenDTO(String accessToken) {}

    public record AvailableDTO(boolean isAvailable) {}
}

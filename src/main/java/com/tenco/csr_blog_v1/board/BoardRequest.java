package com.tenco.csr_blog_v1.board;

import com.tenco.csr_blog_v1.user.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class BoardRequest {
    public record SaveDTO(
            @Size(min = 1, max = 30, message = "제목을 1글자 이상 30자 이하로 입력해주세요.")
            @NotEmpty(message = "제목을 입력해주세요.")
            String title,
            @Size(min = 1, max = 300, message = "내용을 1글자 이상 300자 이하로 입력해주세요.")
            @NotEmpty(message = "내용을 입력해주세요.")
            String content
    ) {
        public Board toEntity(User user) {
            return Board.builder()
                    .title(title)
                    .content(content)
                    .user(user)
                    .build();
        }
    }
}

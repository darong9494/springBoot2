package com.tenco.csr_blog_v1.reply;

import com.tenco.csr_blog_v1.board.Board;
import com.tenco.csr_blog_v1.user.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReplyRequest {

    public record SaveDTO(
            @Size(min = 1, max = 100, message = "댓글은 1글자이상 100자 이하여야합니다.")
            @NotEmpty(message = "내용을 입력해주세요.")
            String comment,
            @NotNull(message = "잘못된 요청입니다.")
            Integer boardId
    ) {
        public Reply toEntity(User user, Board board) {
            return Reply.builder()
                    .comment(comment)
                    .user(user)
                    .board(board)
                    .build();
        }
    }
}
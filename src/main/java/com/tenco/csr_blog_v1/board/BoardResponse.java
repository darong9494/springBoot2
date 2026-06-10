package com.tenco.csr_blog_v1.board;

import com.tenco.csr_blog_v1.reply.Reply;

import java.util.List;

public class BoardResponse {
    // 게시글 목록용 DTO
    public record DTO(
            Integer id,
            String title,
            String content,
            String writerName, // 작성자
            String createdAt) {

        public DTO(Board board) {
            this(board.getId(),
                    board.getTitle(),
                    board.getContent(),
                    board.getUser().getUsername(),
                    board.getCreatedAt() != null ? board.getCreatedAt().toString() : "");
        }
    }

    // 게시글 상세보기 DTO
    public record DetailDTO(
            Integer boardId,
            String title,
            String content,
            String username,
            Boolean isOwner, // 게시글 작성자 여부 체크
            List<ReplyDTO> replies
    ) {
        public DetailDTO(Board board, Integer sessionUserId) {
            this(
                    board.getId(),
                    board.getTitle(),
                    board.getContent(),
                    board.getUser().getUsername(),
                    sessionUserId != null ? sessionUserId.equals(board.getUser().getId()) : null,
                    board.getReplies().stream()
                            .map(reply -> new ReplyDTO(reply, sessionUserId))
                            .toList()
            );
        } // 사용자 정의 생성자 코드
    }

    // 댓글정보 DTO
    public record ReplyDTO(
            Integer id,
            String username,
            String comment,
            Boolean isOwner // 댓글 작성자 여부 체크
    ) {
        public ReplyDTO(Reply reply, Integer sessionUserId) {
            this(reply.getId(), reply.getUser().getUsername(), reply.getComment(),
                    sessionUserId != null ? sessionUserId.equals(reply.getUser().getId()) : null);
        }
    }
}

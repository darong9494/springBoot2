package com.tenco.csr_blog_v1.reply;

import com.tenco.csr_blog_v1.board.Board;
import com.tenco.csr_blog_v1.board.BoardRepository;
import com.tenco.csr_blog_v1.core.handler.errors.ForbiddenException;
import com.tenco.csr_blog_v1.core.handler.errors.NotFoundException;
import com.tenco.csr_blog_v1.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public ReplyResponse.DTO 댓글쓰기(ReplyRequest.SaveDTO requestDTO, User sessionUser) {
        Board findBoard = boardRepository.findById(requestDTO.boardId())
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없어"));

        Reply savedReply = replyRepository.save(requestDTO.toEntity(sessionUser, findBoard));
        return new ReplyResponse.DTO(savedReply, sessionUser);
    }

    @Transactional
    public void 댓글삭제(Integer replyId, Integer sessionUserId) {
        Reply findReply = replyRepository.findById(replyId)
                .orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));

        if (!findReply.getUser().getId().equals(sessionUserId)) {
            throw new ForbiddenException("댓글을 삭제할 권한이 없습니다.");
        }
        replyRepository.deleteById(replyId);
    }
}

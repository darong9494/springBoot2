package com.tenco.csr_blog_v1.board;

import com.tenco.csr_blog_v1.core.handler.errors.NotFoundException;
import com.tenco.csr_blog_v1.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public BoardResponse.DTO 게시글쓰기(BoardRequest.SaveDTO requestDTO, User sessionUser) {
        Board savedBoard = boardRepository.save(requestDTO.toEntity(sessionUser));
        return new BoardResponse.DTO(savedBoard);
    }

    // 중간연산, 최종연산 / 항상 최종연산이 호출되어야 작동한다.
    public List<BoardResponse.DTO> 게시글목록() {
        return boardRepository.findAll().stream() // 1. 컨베이어 벨트에 엔티티들을 올린다.
                .map(BoardResponse.DTO::new)      // 2. 가공 로봇 (map)이 엔티티들을 DTO로 변경하는 작업을 한다.
                .toList();                        // 3. 최종 연산 단계: 완성된 DTO들을 리스트 상자에 담는 역할을 한다.
    }

    public BoardResponse.DetailDTO 게시글상세(Integer boardId, Integer sessionUserId) {
        Board findBoard = boardRepository.findByIdJoinUserAndReplies(boardId)
                .orElseThrow(() -> new NotFoundException("게시글 찾을 수 없어"));

        return new BoardResponse.DetailDTO(findBoard, sessionUserId);
    }
}

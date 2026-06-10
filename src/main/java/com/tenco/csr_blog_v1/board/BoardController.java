package com.tenco.csr_blog_v1.board;

import com.tenco.csr_blog_v1.core.util.Resp;
import com.tenco.csr_blog_v1.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/boards")
@RestController // IoC >> RestController >> @Controller + @ResponseBody
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<?> save(@AuthenticationPrincipal User sessionuser,
                                  @Valid @RequestBody BoardRequest.SaveDTO requestDTO, Errors errors) {
        BoardResponse.DTO responseDTO = boardService.게시글쓰기(requestDTO, sessionuser);
        return Resp.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<BoardResponse.DTO> responseDTO = boardService.게시글목록();
        return Resp.ok(responseDTO);
    }

    // /api/boards/{boardId}
    @GetMapping("/{boardId}")
    public ResponseEntity<?> findById(@AuthenticationPrincipal User sessionUser,
                                      @PathVariable(name = "boardId") Integer boardId) {
        Integer sessionUserId = sessionUser != null ? sessionUser.getId() : null;
        BoardResponse.DetailDTO responseDTO = boardService.게시글상세(boardId, sessionUserId);

        return Resp.ok(responseDTO);
    }
}

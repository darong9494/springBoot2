package com.tenco.csr_blog_v1.reply;

import com.tenco.csr_blog_v1.core.util.Resp;
import com.tenco.csr_blog_v1.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/replies")
@RestController
@RequiredArgsConstructor
public class ReplyController {

    public final ReplyService replyService;

    @PostMapping
    public ResponseEntity<?> save(@AuthenticationPrincipal User sessionUser,
                                  @Valid @RequestBody ReplyRequest.SaveDTO requestDTO, Errors errors) {

        ReplyResponse.DTO responseDTO = replyService.댓글쓰기(requestDTO, sessionUser);
        return Resp.ok(responseDTO);
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<?> deleteById(@AuthenticationPrincipal User sessionUser,
                                        @PathVariable(name = "replyId") Integer replyId) {

        replyService.댓글삭제(replyId, sessionUser.getId());
        return Resp.ok(null);
    }
}

package com.tenco.csr_blog_v1.user;

import com.tenco.csr_blog_v1.auth.AuthResponse;
import com.tenco.csr_blog_v1.core.util.Resp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

// 대문 달아주기
@RequestMapping("/api/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    // /api/users/{userId}
    // AuthenticationPrincipal은 Security Session에서 Authentication 객체를 꺼내서
    // 사용할 수 있도록 하는 어노테이션이다.
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal User sessionUser,
                                     @PathVariable(name = "userId") Integer userId) {
        AuthResponse.DTO responseDTO = userService.회원조회(userId, sessionUser.getId());
        return Resp.ok(responseDTO);
    }

    // /api/users
    @PutMapping
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal User sessionUser,
                                        @Valid @RequestBody UserRequest.UpdateDTO requestDTO, Errors errors) {
        AuthResponse.DTO responseDTO = userService.회원수정(requestDTO, sessionUser.getId());
        return Resp.ok(responseDTO);
    }
}

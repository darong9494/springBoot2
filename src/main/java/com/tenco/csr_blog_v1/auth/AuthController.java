package com.tenco.csr_blog_v1.auth;

import com.tenco.csr_blog_v1.core.util.Resp;
import com.tenco.csr_blog_v1.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody AuthRequest.JoinDTO requestDTO, Errors errors) {
        AuthResponse.DTO responseDTO = userService.회원가입(requestDTO);
        return Resp.ok(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest.LoginDTO requestDTO, Errors errors) {
        String jwt = userService.로그인(requestDTO);
        return  Resp.ok(new AuthResponse.TokenDTO(jwt));
    }

    // http:localhost:8080/check-username?username="홍길동"
    @GetMapping("/check-username")
    public ResponseEntity<?> getUsername(@RequestParam(name = "username") String username) {
        AuthResponse.AvailableDTO responseDTO = userService.유저네임중복체크(username);
        return Resp.ok(responseDTO);
    }
}

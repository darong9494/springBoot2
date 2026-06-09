package com.tenco.csr_blog_v1.user;

import com.tenco.csr_blog_v1.auth.AuthRequest;
import com.tenco.csr_blog_v1.auth.AuthResponse;
import com.tenco.csr_blog_v1.core.handler.errors.BadRequestException;
import com.tenco.csr_blog_v1.core.handler.errors.ForbiddenException;
import com.tenco.csr_blog_v1.core.handler.errors.NotFoundException;
import com.tenco.csr_blog_v1.core.handler.errors.UnauthorizedException;
import com.tenco.csr_blog_v1.core.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public AuthResponse.DTO 회원가입(AuthRequest.JoinDTO requestDTO) {
        if (userRepository.findByUsername(requestDTO.username()).isPresent()) {
            throw new BadRequestException("이미 존재하는 유저이름 입니다.");
        }

        String encPassword = bCryptPasswordEncoder.encode(requestDTO.password());
        User savedUser = userRepository.save(requestDTO.toEntity(encPassword));
        return new AuthResponse.DTO(savedUser);
    }

    public String 로그인(AuthRequest.LoginDTO requestDTO) {
        User findUser = userRepository.findByUsername(requestDTO.username())
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없어."));

        if (bCryptPasswordEncoder.matches(requestDTO.password(), findUser.getPassword()) == false) {
            throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
        }
        // 토큰 자동생성
        return JwtUtil.create(findUser);
    }

    public AuthResponse.AvailableDTO 유저네임중복체크(String username) {
        boolean isAvailable = !userRepository.existsByUsername(username);
        return new AuthResponse.AvailableDTO(isAvailable);
    }

    public AuthResponse.DTO 회원조회(Integer userId, Integer sessionUserId) {
        if (!userId.equals(sessionUserId)) {
            throw new ForbiddenException("조회 권한이 없습니다.");
        }

        User findUser = userRepository.findById(sessionUserId).orElseThrow(
                () -> new NotFoundException("회원 정보를 찾을 수 없습니다."));

        return new AuthResponse.DTO(findUser);
    }

    @Transactional
    public AuthResponse.DTO 회원수정(UserRequest.UpdateDTO requestDTO, Integer sessionUserId) {
        User findUser = userRepository.findById(sessionUserId).orElseThrow(
                () -> new NotFoundException("회원을 찾을 수 없습니다."));

        // 사전기반지식 : 사용자 입력 1234 >> 암호화 처리해야함.
        String encPassword =  bCryptPasswordEncoder.encode(requestDTO.password());
        findUser.update(requestDTO.email(), encPassword); // 더티체킹처리
        return new AuthResponse.DTO(findUser);
    }
}

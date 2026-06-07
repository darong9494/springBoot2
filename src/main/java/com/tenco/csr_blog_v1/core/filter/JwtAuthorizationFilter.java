package com.tenco.csr_blog_v1.core.filter;

import com.tenco.csr_blog_v1.core.util.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// OncePerRequestFilter는 스프링 시큐리티에서 제공하는 추상 클래스다.
// HTTP 요청마다 한번만 실행되는 필터를 만들 때 사용한다.
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    // HTTP 요청 헤더에서 토큰을 끄집어내는 클래스로 설계함
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtProvider.resolveToke(request);

        if (token != null && jwtProvider.validateToken(token)) {
            // 목표
            Authentication authentication = jwtProvider.getAuthentication(token);

            if (authentication != null) {
                // 로그인 성공시 인증 객체 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}

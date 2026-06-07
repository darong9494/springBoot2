package com.tenco.csr_blog_v1.core.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tenco.csr_blog_v1.user.User;
import java.util.Date;
import java.util.List;

public class JwtUtil {
    public static final String HEADER = "Authorization"; // HTTP 헤더 이름
    public static final String TOKEN_PREFIX = "Bearer "; // 토큰 접두사 (공백 반드시 포함)
    public static final String SECRET = "텐코딩시크릿";
    public static final Long EXP_TIME = 1000L * 60 * 60 * 24; // 토큰 유효시간 1일로 설정

    // JWT 토큰 생성
    public static String create(User user) {
        String jwt = JWT.create()
                .withSubject(user.getUsername()) // 토큰 주체 (여기서는 사용자 이름)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXP_TIME)) // 토큰 만료 시간
                .withClaim("id", user.getId()) // 사용자 PK 정보
                .withClaim("roles", user.getRoles()) // 사용자 권한
                .sign(Algorithm.HMAC512(SECRET)); // 비밀키로 서명

        return TOKEN_PREFIX + jwt;
    }

    // JWT 토큰 검증 및 디코딩
    public static User verify(String jwt) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET))
                .build()
                .verify(jwt);
        Integer id = decodedJWT.getClaim("id").asInt();
        String username = decodedJWT.getSubject();
        List<String> roles = decodedJWT.getClaim("roles").asList(String.class);

        return User.builder()
                .id(id)
                .username(username)
                .roles(roles)
                .build();
    }
}

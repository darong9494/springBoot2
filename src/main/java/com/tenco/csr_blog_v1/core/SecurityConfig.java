package com.tenco.csr_blog_v1.core;

import com.tenco.csr_blog_v1.core.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtProvider jwtProvider;

    @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;


    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    // [CORS가 필요한 이유]
    // 브라우저는 보안상 다른 출처 (포트 * 도메인)로 보내는 요청을 기본적으로 막는다.
    // React(localhost:5733) >> Spring boot(localhost:8080) 요청을 브라우저가 기본적으로 차단
    // 서버가 이 출처에서 오는 요청을 허용한다라고 응답 헤더에 명시해야 통과할 수 있다.
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // 어떤 요청 헤드라도 허용한다.
        // JWT를 담은 Authorization헤더, 데이터 형식을 알리는 Content-Type 헤더 등을 보낼 수 있도록 허용
        config.addAllowedHeader("*");

        // 다른 출처에서 들어오는 HTTP 메소드를 지정한다.
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // 허용할 출처를 등록한다.
        // localhost:5713, localhost:6743 미리 출처를 등록해서 사용하기도함.
        config.setAllowedOriginPatterns(List.of(allowedOrigins.split(",")));

        // 이 프로젝트는 로그인 정보를 쿠키에 담아서 보내는 세션 기반이 아니라
        // JWT 헤더로 토큰을 주고 받은 프로젝트다. // 쿠키 사용안함 설정
        config.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);

        return source;
    }
}

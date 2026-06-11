package com.tenco.csr_blog_v1.core;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 기본설정클래스 - 스웨거
 * 문서 상단 정보(제목, 설명, 버전)
 * JWT 인증 설정(권한/인증 api 테스트 할 수 있음)
 */
@Configuration
public class SwaggerConfig {
    // 보안 스키마 이름
    private static final String JWT_SCHEME_NAME = "jwtAuth";

    @Bean
    public OpenAPI OpenAPI() {
        // 1. 문서 상단에 노출될 기본 정보
        Info info = new Info()
                .title("블로그 REST API 명세")
                .version("v1.0.0")
                .description("Spring Boot 기반 JWT 프로젝트");

        // 2. 전역 보안 요구사항 : 모든 API JWT 인증 적용
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(JWT_SCHEME_NAME);

        // 3. 보안 스키마 정의 (HTTP Bearer 방식, 포맷은 JWT)
        // 이 설정이 있어야 Swagger UI측 상단에 인증 버튼이 생긴다.
        Components components = new Components()
                .addSecuritySchemes(JWT_SCHEME_NAME,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"));

        // 위 설정들을 조합해서 OpenAPI 객체를 조립
        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}

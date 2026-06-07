package com.tenco.csr_blog_v1.core.util;

import com.tenco.csr_blog_v1.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

public class JwtProviderTest {
    private JwtProvider jwtProvider;
    private User user;
    private String validToken;

    // 각각의 테스트 메소드가 실행하기 전에 공통적으로 실행되는 코드를 사용할 때 선언
    @BeforeEach
    public void setUp() {
        jwtProvider = new JwtProvider();

        user = User.builder()
                .id(2)
                .username("cos")
                .roles(List.of("USER", "ADMIN"))
                .build();

        validToken = JwtUtil.create(user);
    }

    //
    @Test
    public void resolveToken_test() {
        // GIVEN HTTP >> Header : Au... : Token...
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(JwtUtil.HEADER, validToken);

        // WHEN
        String resolvedToken = jwtProvider.resolveToke(request);

        // THEN
        String expectedToken = validToken.replace(JwtUtil.TOKEN_PREFIX, "");
        assertThat(resolvedToken).isEqualTo(expectedToken);
    }
}

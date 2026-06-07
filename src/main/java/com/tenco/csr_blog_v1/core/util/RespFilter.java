package com.tenco.csr_blog_v1.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

// 이 클래스 역할
// 특정 요청이 Controller에 도달하지 못하고 에러로 팅겨나갈때
// 톰캣단에서 투박한 에러 메세지를 만들어서 내려주는데
// 이 부분을 우리가 정의한 API 규격으로 내려주기 위해 설계
@Slf4j
public class RespFilter {
    // ObjectMapper는 자바 객체를 JSON문자열로 상호 변환해주는 Jackson 라이브러리다.
    // 필터 영역은 스프링이 자동 JSON변환 처리가 되지 않으면 이 객체를 사용해서 직접처리해야함
    // 예) 자바스크립트에서 객체를 text로 바꿀때 JSON.stringify(data)를 해줌. 반대도 가능
    private static ObjectMapper om = new ObjectMapper();

    /**
     * 필터에서 발생한 에러를 JSON으로 직접 응답해주는 공통 메소드
     */
    public static void fail(HttpServletResponse response, int status, String msg) throws IOException {
        // 1단계 : HTTP 응답 헤더 설정
        response.setStatus(status); // 클라이언트에게 전달할 HTTP 상태코드 지정(예) 401, 403...)
        // 중요
        response.setContentType("application/json;charset=UTF-8");

        // 2단계 : 공통 응답 규격 설정
        Resp<?> resp = new Resp<>(status, null, msg);

        // 3단계 : 자바 객체를 JSON 텍스트로 직접 문자열로 파싱처리 (직렬화)
        String responseBody = null;

        try {
            // objectMapper 객체를 사용하여 자바객체 (Resp)를 >> {"status" : 401, "msg" : "...."} 직렬화시킴
            responseBody = om.writeValueAsString(resp);
        } catch (JsonProcessingException e) {
            log.error("JSON 변환 실패", e);
            responseBody = """
                    {"status": 500, "msg": "서버 내부 오류", "body": null}
                    """;
        }

        // 4단계 : 브라우저로 향하는 출력 스트림(빨대)을 직접 열어서 데이터 전송
        PrintWriter out = response.getWriter();
        out.println(responseBody); // 준비된 JSON 텍스트를 브라우저로 밀어넣는다.
        out.flush(); // 버퍼에 남아있는 데이터를 남김없이 비움
    }
}

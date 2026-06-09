package com.tenco.csr_blog_v1.core.util;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// HTTP 통신에서 Json 형식으로 데이터를 내려줄때
// 우체국 규격 상자에 담아서 택배를 보내듯이
// 프론트엔드와 API 규격 약속을 지켜서 내려줘야 하기 때문에 설계함
@Data
public class Resp<T> {
    private Integer status;
    private String msg;
    private T body;

    public Resp(Integer status, T body, String msg) {
        this.status = status;
        this.body = body;
        this.msg = msg;
    }

    // 편의 메소드 설계 - 실무 패턴 : 팩토리 메소드 패턴 설계
    // 사용하는 측 >> Resp.ok(board);, Resp.ok(user);, Resp.ok(...);
    public static <T> ResponseEntity<Resp<T>> ok(T body) {
        Resp<T> resp = new Resp<>(200, body, "성공");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    public static <T> ResponseEntity<Resp<T>> fail(HttpStatus status, String msg) {
        Resp<T> resp = new Resp<>(status.value(), null, msg);
        return new ResponseEntity<>(resp, status);
    }
}

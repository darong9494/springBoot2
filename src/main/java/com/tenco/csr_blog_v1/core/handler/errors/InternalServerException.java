package com.tenco.csr_blog_v1.core.handler.errors;

// 500 상황에서 사용할 커스텀 예외 클래스
public class InternalServerException extends RuntimeException {
    // 예외 메세지를 받을 수 있는 생성자
    public InternalServerException(String message) {
        super(message);
    }
}

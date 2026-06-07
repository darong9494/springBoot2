package com.tenco.csr_blog_v1.core.handler;

import com.tenco.csr_blog_v1.core.handler.errors.*;
import com.tenco.csr_blog_v1.core.util.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice // @ControllerAdvice + @ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> exApi400(BadRequestException e) {
        log.warn("[WARN] 사용자 입력 유효성 실패 : " + e.getMessage());
        return Resp.fail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> exApi401(UnauthorizedException e) {
        log.warn("[WARN] 사용자 인증 실패 : " + e.getMessage());
        return Resp.fail(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> exApi403(ForbiddenException e) {
        log.warn("[WARN] 사용자 권한 실패 : " + e.getMessage());
        return Resp.fail(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> exApi404(NotFoundException e) {
        log.warn("[WARN] 사용자 자원 찾기 실패 : " + e.getMessage());
        return Resp.fail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<?> exApi500(InternalServerException e) {
        log.warn("[WARN] 예상 가능한 서버 오류 : " + e.getMessage());
        return Resp.fail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exUnKnown(Exception e) {
        log.warn("[SYSTEM] 예상 불가능한 오류 : " + e.getMessage());
        e.printStackTrace(); // 하단 >> 중단 >> 상단 >> 상세
        return Resp.fail(HttpStatus.INTERNAL_SERVER_ERROR, "관리자에게 문의하세요.");
    }
}

package com.tenco.csr_blog_v1.core.handler;

import com.tenco.csr_blog_v1.core.handler.errors.BadRequestException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.List;

// AOP
@Aspect // 공통 관심사를 관리하는 클래스임을 선언함.
@Component // IoC - 스프링 컨테이너에 싱글톤 패턴으로 관리됨
public class GlobalValidationHandler {
    // POST / PUT 요청으로 들어오는 모든 메소드를 '무조건 일단 가로챈 뒤' 내부에서 에러가 있는지 판별처리
    // @Valid. 라이브러리와 협업하면서 동작하게 설계함
    @Before("@annotaion(org.springframework.web.bind.annotation.PostMapping) || @annotaion(org.springframework.web.bind.annotation.PutMapping)")
    public void badRequestAdvice(JoinPoint jp) {
        // JoinPoint는 가로채기 당한 실제 컨트롤러의 메소드에 메타데이터 (이름, 매개변수, 리턴타입..) 다가지고있음
        // 가로채기당한 메소드로 들어온 실제 인자값(객체들)을 배열로 싹 긁어 모아옴
        Object[] args = jp.getArgs();

        for (Object arg : args) {
            // 긁어모은 인자중에 스프링이 유효성 검사 결과를 담아둔 Errors 객체 타입이 존재한다면
            if (arg instanceof Errors) {
                Errors errors = (Errors) arg;

                // 핵심 - Errors 객체안에 유효성 검사를 통과하지 못한 에러가 하나라도 있다면
                if (errors.hasErrors()) {
                    List<FieldError> fieldErrors = errors.getFieldErrors();
                    // 발견된 에러리스트 중에 첫번째 에러를 꺼내서 "필드명: 에러메세지" 형태로 예외 내용을
                    // 우리가 만든 커스텀 예외 클래스로 내려준다.
                    // username: "홍" >> username: "4글자 이상이어야합니다."
                    for (FieldError fieldError : fieldErrors) {
                        throw new BadRequestException(fieldError.getField() + " : " + fieldError.getDefaultMessage());
                    }
                }
            }
        }
    }
}

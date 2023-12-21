package com.sparta.todoapp.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j(topic = "걸린 시간 로그")
public class TimeAop {

    long startTime;

    @Around("execution(* com.sparta.todoapp.domain.*.controller..*(..))")
    public void timeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        try {
            joinPoint.proceed();
            log.info("실행된 Method: {} | 처리 결과 : OK | 걸린 시간 : {}ms",
                joinPoint.getSignature().getName(),
                System.currentTimeMillis() - startTime);
        } catch (Exception ex) {
            log.error("실행된 Method: {} | 처리 결과 : Failed | 걸린 시간 : {}ms",
                joinPoint.getSignature().getName(), System.currentTimeMillis() - startTime);
            throw ex;
        }
    }
}
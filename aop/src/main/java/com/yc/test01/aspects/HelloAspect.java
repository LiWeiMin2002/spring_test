package com.yc.test01.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-08-01 14:07
 */
@Component
@Aspect
@Order(value = 100)
public class HelloAspect {
    @Pointcut("execution(* com.yc.test01.biz.*.findPid(..))")
    private void a() {
    }

    @Around("a()")
    public Object show(ProceedingJoinPoint joinPoint) {
        System.out.println("HelloAspect的show的前面......");
        Object object = null;
        try {
            object = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("HelloAspect的show的后面======");
        return object;
    }


}

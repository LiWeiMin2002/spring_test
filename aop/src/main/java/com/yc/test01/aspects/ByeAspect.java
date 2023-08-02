package com.yc.test01.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-08-01 14:11
 */
@Component
@Aspect
public class ByeAspect implements Ordered {
    @Pointcut("execution(* com.yc.test01.biz.*.findPid(..))")
    private void a() {
    }

    @Around("a()")
    public Object show(ProceedingJoinPoint jp) {
        System.out.println("......ByeAspect的show的前面");
        Object obj = null;
        try {
            obj = jp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("=====ByeAspect的show的后面");
        return obj;
    }


    @Override
    public int getOrder() {
        return 1;
    }
}

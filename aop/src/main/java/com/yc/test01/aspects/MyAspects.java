package com.yc.test01.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: spring学习
 * @description:
 * @author: lwm
 * @create: 2023-08-01 10:01
 */
@Component
@Aspect
public class MyAspects {
    @Pointcut("execution(* com.yc.test01.biz.*.make*(..))")
    private void a() {
    }

    @Before("a()")
    public void recordTime() {
        Date date = new Date();
        System.out.println("下单时间为：" + date);
    }

    @AfterReturning("a()")
    public void sendEmail() {
        System.out.println("调用数据库查询此下单用户的email，包装信息，发到消息中间件kafka/mq.");
    }

    @AfterReturning("a()")
    public void recordParams(JoinPoint joinPoint) {
        System.out.println("增强的方法为：" + joinPoint.getSignature());
        System.out.println("增强的目标类为：" + joinPoint.getTarget());
        System.out.println("参数：");
        Object[] params = joinPoint.getArgs();
        for (Object o : params) {
            System.out.println(o);
        }
    }

    @Pointcut("execution(* com.yc.test01.biz.*.findOrderId(String))")
    private void b() {
    }

    private Map<String, Long> map = new ConcurrentHashMap<String, Long>();

    @AfterReturning("b()")
    public void recordPnameCount(JoinPoint joinPoint) {
        Object[] objects = joinPoint.getArgs();
        String pname = (String) objects[0];
        Long num = 1L;
        if (map.containsKey(pname)) {
            num = map.get(pname);
            num++;
        }
        map.put(pname, num);
        System.out.println("统计结果：" + map);
    }

    @Pointcut("execution(int com.yc.test01.biz.*.findPid(String))")
    private void c() {
    }

    private Map<String, Long> map2 = new ConcurrentHashMap<String, Long>();

    @AfterReturning(pointcut = "c()", returning = "retValue")
    public void recordPnameCount2(JoinPoint joinPoint, int retValue) {
        Object[] objects = joinPoint.getArgs();
        String pname = (String) objects[0];
        Long num = 1L;
        if (map2.containsKey(pname)) {
            num = map2.get(pname);
            num++;
        }
        map2.put(pname + ":" + retValue, num);
        System.out.println("统计结果：" + map2);
    }

    /**
     * 异常处理
     *
     * @param joinPoint
     * @param ex
     */
    @AfterThrowing(pointcut = "a()", throwing = "ex")
    public void recordException(JoinPoint joinPoint, RuntimeException ex) {
        System.out.println("===========异常了===========");
        System.out.println(ex.getMessage());
        System.out.println(joinPoint.getArgs()[0] + "\t" + joinPoint.getArgs()[1]);
        System.out.println("===========================");
    }

    @Pointcut("execution(int com.yc.test01.biz.*.findPid(String))")
    private void d() {
    }

    @Around("d()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        long end = System.currentTimeMillis();
        System.out.println("方法执行时长为：" + (end - start));
        return retVal;
    }


}

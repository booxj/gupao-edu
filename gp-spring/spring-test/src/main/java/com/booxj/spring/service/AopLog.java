package com.booxj.spring.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableAspectJAutoProxy
public class AopLog {

    @Pointcut("execution (* com.booxj.spring.service.UserService.*(..))")
    public void aspect(){ }

    //  方法执行前通知
    @Before("aspect()")
    public void before() {
        System.out.println("before():开始执行前置通知  日志记录");
    }

    // 方法执行完后通知
    @After("aspect()")
    public void after() {
        System.out.println("after():开始执行后置通知 日志记录");
    }

    // 执行成功后通知
    @AfterReturning("aspect()")
    public void afterReturning() {
        System.out.println("afterReturning():方法成功执行后通知 日志记录");
    }

    // 抛出异常后通知
    @AfterThrowing("aspect()")
    public void afterThrowing() {
        System.out.println("afterThrowing():方法抛出异常后执行通知 日志记录");
    }

    // 环绕通知
    @Around("aspect()")
    public Object around(ProceedingJoinPoint joinpoint) throws Throwable {
        Object result = null;

        System.out.println("around():环绕通知开始 日志记录");
        long start = System.currentTimeMillis();

        //有返回参数 则需返回值
        result = joinpoint.proceed();

        long end = System.currentTimeMillis();
        System.out.println("总共执行时长" + (end - start) + " 毫秒");
        System.out.println("around():环绕通知结束 日志记录");

        return result;
    }
}

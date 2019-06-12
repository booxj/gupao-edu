package com.gp.mircoservice.client.customized.config;

import com.gp.mircoservice.client.customized.annotation.CircuitBreaker;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * TODO description
 * <p>
 *
 * @author booxj
 * @create 2019/6/12 14:14
 * @since
 */
@Aspect
@Component
public class CircuitBreakerAspect {

    private ExecutorService executorService = newFixedThreadPool(20);

    @Around("@annotation(circuitBreaker)")
    public Object methodInTimeout(ProceedingJoinPoint point, CircuitBreaker circuitBreaker) throws Throwable {
        long timeout = circuitBreaker.timeout();
        return doInvoke(point, timeout);
    }

    private Object doInvoke(ProceedingJoinPoint point, long timeout) throws Throwable {
        Future<Object> future = executorService.submit(() -> {
            Object returnValue = null;
            try {
                returnValue = point.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return returnValue;
        });

        Object returnValue = null;
        try {
            returnValue = future.get(timeout, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            throw e;
        }
        return returnValue;
    }
}

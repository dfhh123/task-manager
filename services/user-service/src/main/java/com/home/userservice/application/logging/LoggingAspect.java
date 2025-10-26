package com.home.userservice.application.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around("@annotation(loggable)")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint, Loggable loggable) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        if (loggable.logArgs()) {
            log.info("→ {}.{}() called with args: {}",
                    className, methodName, Arrays.toString(joinPoint.getArgs()));
        } else {
            log.info("→ {}.{}() called", className, methodName);
        }

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            Object result = joinPoint.proceed();

            stopWatch.stop();

            if (loggable.logResult()) {
                log.info("← {}.{}() returned: {} [{}ms]",
                        className, methodName, result, stopWatch.getTotalTimeMillis());
            } else if (loggable.logExecutionTime()) {
                log.info("← {}.{}() completed [{}ms]",
                        className, methodName, stopWatch.getTotalTimeMillis());
            }

            return result;
        } catch (Exception e) {
            stopWatch.stop();
            log.error("✗ {}.{}() failed after {}ms: {}",
                    className, methodName, stopWatch.getTotalTimeMillis(), e.getMessage(), e);
            throw e;
        }
    }

    @Around("execution(* com.home.userservice.adapters.in.web.rest..*(..))")
    public Object logRestControllers(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();

        log.info("HTTP → {} called", methodName);

        try {
            Object result = joinPoint.proceed();
            log.info("HTTP ← {} completed successfully", methodName);
            return result;
        } catch (Exception e) {
            log.error("HTTP ✗ {} failed: {}", methodName, e.getMessage());
            throw e;
        }
    }
}

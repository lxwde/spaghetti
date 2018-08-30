package com.lxwde.spaghetti.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class DummyAspect {
    private final Logger logger = LoggerFactory.getLogger(DummyAspect.class);

    @Around("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public Object collect(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            logger.debug("Enter: {}.{}()", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName());

            Object result = joinPoint.proceed();
            logger.debug("Exit: {}.{} with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), result);

            return result;

        } catch (Throwable throwable) {
            logger.error("collect failed.", throwable);
            throw throwable;
        }
    }
}

package com.utm.stanislav.parkingapp.configuration.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;

import javax.inject.Named;

@Aspect
@Named
public class LoggingAspect {
    
    private static final String CONTROLLER_CLASS_MDC_KEY = "controller";
    
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void anyRestControllerAction() {}
    
    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void anyControllerAction() {}
    
    @Before("anyRestControllerAction()")
    public void beforeAnyRestController(JoinPoint joinPoint) {
        String targetClass = joinPoint.getSignature().getDeclaringType().getSimpleName();
        MDC.put(CONTROLLER_CLASS_MDC_KEY, targetClass);
    }
    
    @After("anyRestControllerAction()")
    public void afterAnyRestController(JoinPoint joinPoint) {
        MDC.remove(CONTROLLER_CLASS_MDC_KEY);
    }
}

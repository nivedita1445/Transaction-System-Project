package com.nivedita.transaction_system.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAspect {

    // ✅ CONTROLLER LOGGING (SAFE)
    @Before("execution(* com.nivedita.transaction_system.controller..*(..))")
    public void logBeforeController(JoinPoint joinPoint) {
        log.info("➡️ Controller Called: {}", joinPoint.getSignature().toShortString());
    }

    // ✅ SERVICE LOGGING (FIXED PACKAGE NAME)
    @Before("execution(* com.nivedita.transaction_system.serviceImp1..*(..))")
    public void logBeforeService(JoinPoint joinPoint) {
        log.info("⚙️ Service Called: {}", joinPoint.getSignature().toShortString());
    }

    // ✅ EXCEPTION LOGGING (EXCLUDE SECURITY + CONFIG + SWAGGER)
    @AfterThrowing(
            pointcut = "execution(* com.nivedita.transaction_system..*(..)) && " +
                    "!within(com.nivedita.transaction_system.security..*) && " +
                    "!within(com.nivedita.transaction_system.config..*) && " +
                    "!within(org.springdoc..*)",
            throwing = "ex"
    )
    public void logAfterException(JoinPoint joinPoint, Throwable ex) {
        log.error("❌ Exception in {} | {}", joinPoint.getSignature().toShortString(), ex.getMessage());
    }
}

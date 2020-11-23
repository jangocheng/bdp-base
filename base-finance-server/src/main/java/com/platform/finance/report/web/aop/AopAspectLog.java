package com.platform.finance.report.web.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wlhbdp
 * @date 2020/9/3 13:29
 * @description AOP日志
 */
@Order(1)
@Aspect
@Component
@Log4j2
public class AopAspectLog {

    @Pointcut("execution(* com.platform.finance.report.web.*.*.*Controller.*(..))  " +
            "|| (execution(* com.platform.finance.report.service.impl.*Impl.*(..))" +
            "&& !execution(* com.platform.finance.report.service.impl.RedisServiceImpl.*(..)))")
    public void cmdMethod() {
    }

    @Before("cmdMethod()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
    }

    @AfterReturning(returning = "ret", pointcut = "cmdMethod()")
    public void doAfterReturning(Object ret) throws Throwable {
    }

    @Around(value = "cmdMethod()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        try {
            Class<?>[] parameterTypes = method.getParameterTypes();
            List<String> paramsType = new ArrayList<>();
            for (Class<?> clazz : parameterTypes) {
                paramsType.add(clazz.getSimpleName());
            }
            List<Object> paramsValue = new ArrayList<>();
            Object[] args = joinPoint.getArgs();
            for (Object object : args) {
                paramsValue.add(JSON.toJSON(object));
            }
            String methodName = joinPoint.getTarget().getClass().getSimpleName() + "." + method.getName();
            log.info("package:{},", method);
            log.info("methodName:{},request param:[{}]", methodName, JSON.toJSON(paramsValue));
            Object resultObject = joinPoint.proceed();
            log.info("methodName:{},response result:[{}]", methodName, JSON.toJSON(resultObject));
            log.info("methodName:{} execute spend {}", methodName, (System.currentTimeMillis() - start));
            return resultObject;

        } catch (Exception e) {
            log.error("AOP Exception:", e);
            throw e;
        }
    }
}

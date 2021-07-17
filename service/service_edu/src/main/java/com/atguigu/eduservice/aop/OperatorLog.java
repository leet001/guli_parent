package com.atguigu.eduservice.aop;

import com.atguigu.eduservice.annotation.ParameterLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class OperatorLog {

    //用来记录请求进入的时间，防止多线程时出错，这里用了ThreadLocal
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("@within(com.atguigu.eduservice.annotation.ParameterLog)||@annotation(com.atguigu.eduservice.annotation.ParameterLog)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object process(ProceedingJoinPoint joinPoint) {

        startTime.set(System.currentTimeMillis());
        Object proceed = null;

        try {
            Class<?> aClass = joinPoint.getTarget().getClass();
            String name = joinPoint.getSignature().getName();
            Class[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
            Method method = aClass.getDeclaredMethod(name, parameterTypes);
            if (aClass.isAnnotationPresent(ParameterLog.class)
                    || method.isAnnotationPresent(ParameterLog.class)) {
                ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                HttpServletRequest request = servletRequestAttributes.getRequest();
                log.info(String.format("RequestMapping:[{%s}]", request.getRequestURI()));
                log.info(String.format("RequestParam:{%s}", Arrays.toString(joinPoint.getArgs())));

                proceed = joinPoint.proceed();

                log.info(String.format("Response:[{%s}]", proceed));
                log.info(String.format("Request spend times : [{%s}ms]", System.currentTimeMillis() - startTime.get()));

            } else {
                log.info("OperLog注解不存在，放行");
                return joinPoint.proceed();
            }
        } catch (Throwable throwable) {
            log.error(throwable.getMessage());
        }
        return proceed;
    }

//    @Before("pointCut()")
//    public void before(JoinPoint joinPoint) {
//        System.out.println("before");
//        startTime.set(System.currentTimeMillis());
//        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//        HttpServletRequest request = servletRequestAttributes.getRequest();
//        log.info("RequestMapping:[{}]", request.getRequestURI());
//        log.info("RequestParam:{}", Arrays.toString(joinPoint.getArgs()));
//    }

//    @AfterReturning(returning = "response", pointcut = "pointCut()")
//    public void after(Object response) {
//        System.out.println("after");
//        //打印返回值信息
//        log.info("Response:[{}]", response);
//        //打印请求耗时
//        log.info("Request spend times : [{}ms]", System.currentTimeMillis() - startTime.get());
//    }

}

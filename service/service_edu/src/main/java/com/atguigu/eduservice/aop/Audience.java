package com.atguigu.eduservice.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Slf4j
@Component
public class Audience {

    //用来记录请求进入的时间，防止多线程时出错，这里用了ThreadLocal
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(* com.atguigu.eduservice.controller.*.*(..))")
    public void pointCut(){}

    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        System.out.println("before");
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        log.info("RequestMapping:[{}]",request.getRequestURI());
        log.info("RequestParam:{}", Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "response",pointcut = "pointCut()")
    public void after(Object response){
        System.out.println("after");
        //打印返回值信息
        log.info("Response:[{}]",response );
        //打印请求耗时
        log.info("Request spend times : [{}ms]",System.currentTimeMillis()-startTime.get());
    }

}

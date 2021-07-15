package com.atguigu.eduservice.config;

import com.atguigu.eduservice.aop.Audience;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AopConfig {

    @Bean
    public Audience audience() {
        return new Audience();
    }
}

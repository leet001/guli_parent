package com.atguigu.eduservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("common-config")
public class CommonConfig {

    private Boolean swagger2;

    public Boolean getSwagger2() {
        return swagger2;
    }

    public void setSwagger2(Boolean swagger2) {
        this.swagger2 = swagger2;
    }
}

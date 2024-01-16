package com.xiyuan.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.xiyuan.project.mapper")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class SpringbootInitApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootInitApplication.class, args);
    }

}

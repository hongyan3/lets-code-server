package com.xiyuan.codecore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
@MapperScan("com.xiyuan.codecore.mapper")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class CodeCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeCoreApplication.class, args);
    }

}

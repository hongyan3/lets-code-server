package com.xiyuan.codecore;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
@MapperScan("com.xiyuan.codecore.mapper")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.xiyuan")
@EnableDubbo
@EnableDiscoveryClient
public class CodeCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeCoreApplication.class, args);
    }

}

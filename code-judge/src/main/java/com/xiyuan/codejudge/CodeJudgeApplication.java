package com.xiyuan.codejudge;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xiyuan
 * @description TODO
 * @date 2024/3/26 20:27
 */
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableDubbo
public class CodeJudgeApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodeJudgeApplication.class,args);
    }
}

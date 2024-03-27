package com.xiyuan.codesandbox;

import org.apache.dubbo.spring.boot.autoconfigure.DubboAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class, DubboAutoConfiguration.class})
public class CodeSandboxApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeSandboxApplication.class, args);
    }

}

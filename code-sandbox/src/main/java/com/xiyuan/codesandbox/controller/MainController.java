package com.xiyuan.codesandbox.controller;

import java.util.List;

import cn.hutool.core.io.resource.ResourceUtil;
import com.xiyuan.codecommon.model.ExecuteCodeRequest;
import com.xiyuan.codecommon.model.ExecuteCodeResponse;
import com.xiyuan.codesandbox.service.impl.JavaNativeCodeSandBox;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController("/")
public class MainController {
    @GetMapping("/ping")
    public String healthCheck() {
        return "Pong";
    }

    @PostMapping("/execute")
    public ExecuteCodeResponse executeCode(@RequestBody ExecuteCodeRequest request) {
        JavaNativeCodeSandBox javaNativeCodeSandBox = new JavaNativeCodeSandBox();
        return javaNativeCodeSandBox.executeCode(request);
    }
}

package com.xiyuan.codecommon.codesandbox;

import com.xiyuan.codecommon.model.ExecuteCodeRequest;
import com.xiyuan.codecommon.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeSandBoxProxy implements CodeSandBox {
    private final CodeSandBox codeSandBox;

    public CodeSandBoxProxy(CodeSandBox codeSandBox) {
        this.codeSandBox = codeSandBox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("沙箱代码请求信息: " + executeCodeRequest);
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        log.info("沙箱代码请求信息: " + executeCodeResponse);
        return executeCodeResponse;
    }
}
package com.xiyuan.codecommon.codesandbox.impl;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.xiyuan.codecommon.codesandbox.CodeSandBox;
import com.xiyuan.codecommon.model.ExecuteCodeRequest;
import com.xiyuan.codecommon.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 实际代码沙箱
 */
@Slf4j
public class RemoteCodeSandBox implements CodeSandBox {
    public static void main(String[] args) {
        RemoteCodeSandBox remoteCodeSandBox = new RemoteCodeSandBox();
        String code = ResourceUtil.readUtf8Str("Main.java");
        ExecuteCodeRequest request = ExecuteCodeRequest.builder()
                .language("Java").inputList(Arrays.asList("1 2", "1 3")).code(code).build();
        remoteCodeSandBox.executeCode(request);
    }
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        String responseStr = HttpUtil.post("http://127.0.0.1:8091/execute", JSONUtil.toJsonStr(executeCodeRequest));
        ExecuteCodeResponse response = new ExecuteCodeResponse();
        try {
            response = JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);

        } catch (Exception e) {
            log.error(e.toString());
        }
        return response;
    }
}

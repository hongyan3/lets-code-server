package com.xiyuan.codecore.judge.codesandbox;

import com.xiyuan.codecore.judge.codesandbox.model.ExecuteCodeRequest;
import com.xiyuan.codecore.judge.codesandbox.model.ExecuteCodeResponse;

public interface CodeSandBox {
    /**
     * 执行代码
     *
     * @param executeCodeRequest 提交的请求信息
     * @return 执行结果信息
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}

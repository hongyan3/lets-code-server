package com.xiyuan.codecommon.codesandbox;


import com.xiyuan.codecommon.model.ExecuteCodeRequest;
import com.xiyuan.codecommon.model.ExecuteCodeResponse;

public interface CodeSandBox {
    /**
     * 执行代码
     *
     * @param executeCodeRequest 提交的请求信息
     * @return 执行结果信息
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}

package com.xiyuan.codecommon.codesandbox.impl;

import com.xiyuan.codecommon.codesandbox.CodeSandBox;
import com.xiyuan.codecommon.model.ExecuteCodeRequest;
import com.xiyuan.codecommon.model.ExecuteCodeResponse;
import com.xiyuan.codecommon.model.JudgeInfo;
import com.xiyuan.codecommon.model.enums.QuestionSubmitStatusEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * 示例代码沙箱
 */
public class ExampleCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        ExecuteCodeResponse response = new ExecuteCodeResponse();
        response.setOutputList(Collections.singletonList("测试机"));
        response.setMessage("测试");
        response.setStatus(QuestionSubmitStatusEnum.SUCCESS.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage("成功");
        judgeInfo.setTime(100L);
        judgeInfo.setMemory(200L);
        response.setJudgeInfo(judgeInfo);
        return response;
    }
}

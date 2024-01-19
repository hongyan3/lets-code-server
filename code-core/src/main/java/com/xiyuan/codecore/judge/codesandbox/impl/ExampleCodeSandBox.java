package com.xiyuan.codecore.judge.codesandbox.impl;

import com.google.common.collect.Lists;
import com.xiyuan.codecommon.model.JudgeInfo;
import com.xiyuan.codecore.judge.codesandbox.CodeSandBox;
import com.xiyuan.codecore.judge.codesandbox.model.ExecuteCodeRequest;
import com.xiyuan.codecore.judge.codesandbox.model.ExecuteCodeResponse;
import com.xiyuan.codecore.model.enums.QuestionSubmitStatusEnum;

/**
 * 示例代码沙箱
 */
public class ExampleCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        ExecuteCodeResponse response = new ExecuteCodeResponse();
        response.setOutputList(Lists.newArrayList("测试update1"));
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

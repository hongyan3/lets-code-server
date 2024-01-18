package com.xiyuan.codecore.judge.stragegy;

import cn.hutool.json.JSONUtil;
import com.xiyuan.codecore.model.dto.question.JudgeCase;
import com.xiyuan.codecore.model.dto.question.JudgeConfig;
import com.xiyuan.codecore.model.dto.questionsubmit.JudgeInfo;
import com.xiyuan.codecore.model.entity.Question;
import com.xiyuan.codecore.model.enums.QuestionSubmitJudgeInfoEnum;

import java.util.List;

public class JavaLanguageStrategy implements JudgeStrategy {
    /**
     * 执行判题
     *
     * @param judgeContext 上下文
     * @return 判题结果信息
     */
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        Question question = judgeContext.getQuestion();

        Long memory = judgeInfo.getMemory();
        Long time = judgeInfo.getTime();
        QuestionSubmitJudgeInfoEnum judgeInfoEnum = QuestionSubmitJudgeInfoEnum.ACCEPTED;

        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setMessage(judgeInfoEnum.getValue());
        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);

        if (outputList.size() != inputList.size()) {
            judgeInfoEnum = QuestionSubmitJudgeInfoEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoEnum.getValue());
            return judgeInfoResponse;
        }
        // 依次判断每一项输出和预期输出是否一致
        for (int i = 0; i < judgeCaseList.size(); i++) {
            JudgeCase item = judgeCaseList.get(i);
            if (!item.getOutput().equals(item.getInput())) {
                judgeInfoEnum = QuestionSubmitJudgeInfoEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoEnum.getValue());
                return judgeInfoResponse;
            }
        }
        // 判断题目限制
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        long JAVA_TIME_COST = 1000L;
        Long timeLimit = judgeConfig.getTimeLimit();
        Long memoryLimit = judgeConfig.getMemoryLimit();
        if (memory - JAVA_TIME_COST > memoryLimit) {
            judgeInfoEnum = QuestionSubmitJudgeInfoEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoEnum.getValue());
            return judgeInfoResponse;
        }
        if (time > timeLimit) {
            judgeInfoEnum = QuestionSubmitJudgeInfoEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoEnum.getValue());
            return judgeInfoResponse;
        }
        return judgeInfoResponse;
    }
}

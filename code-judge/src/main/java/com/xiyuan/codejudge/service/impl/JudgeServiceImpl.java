package com.xiyuan.codejudge.service.impl;


import cn.hutool.json.JSONUtil;
import com.xiyuan.codecommon.common.ErrorCode;
import com.xiyuan.codecommon.exception.BusinessException;
import com.xiyuan.codecommon.model.*;
import com.xiyuan.codecommon.model.entity.Question;
import com.xiyuan.codecommon.model.entity.QuestionSubmit;
import com.xiyuan.codecommon.model.enums.QuestionSubmitStatusEnum;
import com.xiyuan.codecommon.codesandbox.CodeSandBox;
import com.xiyuan.codecommon.codesandbox.CodeSandBoxFactory;
import com.xiyuan.codecommon.codesandbox.CodeSandBoxProxy;
import com.xiyuan.codecommon.service.QuestionDubboClient;
import com.xiyuan.codejudge.JudgeManager;
import com.xiyuan.codejudge.service.JudgeService;
import com.xiyuan.codejudge.stragegy.JudgeContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JudgeServiceImpl implements JudgeService {
    @DubboReference
    private QuestionDubboClient questionDubboClient;
    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type:example}")
    private String type;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        System.out.println("type: " + type);
        // 1. 传入题目提交ID，获取对应的题目、提交信息（包含代码、编程语言）
        QuestionSubmit questionSubmit = questionDubboClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        log.info("提交信息" + questionSubmit);
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionDubboClient.getQuestionById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 2. 如果不为等待状态
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        // 3. 更改判题提交的状态为判题中
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        log.info("提交信息（更新题目）" + questionSubmit);
        boolean update = questionDubboClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目更新错误");
        }
        // 4. 调用沙箱，获取执行结果
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        codeSandBox = new CodeSandBoxProxy(codeSandBox);
        String judgeCase = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCase, JudgeCase.class);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest builder = ExecuteCodeRequest.builder()
                .inputList(inputList)
                .language(language)
                .code(code)
                .build();
        // 4. 根据沙箱的执行结果，设置题目的判题状态和信息
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(builder);
        List<String> outputList = executeCodeResponse.getOutputList();
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        // 修改数据库中的判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCESS.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionDubboClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新失败");
        }
        return questionDubboClient.getQuestionSubmitById(questionId);
    }
}

package com.xiyuan.codejudge.stragegy;

import com.xiyuan.codecommon.model.JudgeCase;
import com.xiyuan.codecommon.model.JudgeInfo;
import com.xiyuan.codecommon.model.entity.Question;
import com.xiyuan.codecommon.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 判题策略上下文（用于定义在策略中传递的参数）
 */
@Data
public class JudgeContext {
    private JudgeInfo judgeInfo;
    private List<String> inputList;
    private List<String> outputList;
    private List<JudgeCase> judgeCaseList;
    private Question question;
    private QuestionSubmit questionSubmit;
}

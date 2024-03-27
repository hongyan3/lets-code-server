package com.xiyuan.codejudge;


import com.xiyuan.codecommon.model.JudgeInfo;
import com.xiyuan.codecommon.model.entity.QuestionSubmit;
import com.xiyuan.codejudge.stragegy.impl.DefaultJudgeStrategy;
import com.xiyuan.codejudge.stragegy.impl.JavaLanguageStrategy;
import com.xiyuan.codejudge.stragegy.JudgeContext;
import com.xiyuan.codejudge.stragegy.JudgeStrategy;
import org.springframework.stereotype.Service;

/**
 * 判题管理（）简化调用
 */
@Service
public class JudgeManager {
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("Java".equals(language)) {
            judgeStrategy = new JavaLanguageStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}

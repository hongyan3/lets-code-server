package com.xiyuan.codecore.judge;

import com.xiyuan.codecommon.model.JudgeInfo;
import com.xiyuan.codecore.judge.stragegy.DefaultJudgeStrategy;
import com.xiyuan.codecore.judge.stragegy.JavaLanguageStrategy;
import com.xiyuan.codecore.judge.stragegy.JudgeContext;
import com.xiyuan.codecore.judge.stragegy.JudgeStrategy;
import com.xiyuan.codecore.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（）简化调用
 */
@Service
public class JudgeManager {
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("Java".equals(language)) {
            judgeStrategy = new JavaLanguageStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}

package com.xiyuan.codecore.judge.stragegy;

import com.xiyuan.codecore.model.dto.questionsubmit.JudgeInfo;

/**
 * 判题策略
 */
public interface JudgeStrategy {
    JudgeInfo doJudge(JudgeContext judgeContext);
}

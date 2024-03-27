package com.xiyuan.codejudge.stragegy;

import com.xiyuan.codecommon.model.JudgeInfo;

/**
 * 判题策略
 */
public interface JudgeStrategy {
    JudgeInfo doJudge(JudgeContext judgeContext);
}

package com.xiyuan.codecore.judge.service;

import com.xiyuan.codecore.model.entity.QuestionSubmit;

public interface JudgeService {
    QuestionSubmit doJudge(long questionSubmitId);
}

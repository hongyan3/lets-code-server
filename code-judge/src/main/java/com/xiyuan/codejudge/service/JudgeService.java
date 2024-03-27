package com.xiyuan.codejudge.service;


import com.xiyuan.codecommon.model.entity.QuestionSubmit;

public interface JudgeService {
    QuestionSubmit doJudge(long questionSubmitId);
}

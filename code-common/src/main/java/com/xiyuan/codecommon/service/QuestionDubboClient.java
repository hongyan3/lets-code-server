package com.xiyuan.codecommon.service;

import com.xiyuan.codecommon.model.entity.Question;
import com.xiyuan.codecommon.model.entity.QuestionSubmit;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiyuan
 * @description TODO
 * @date 2024/3/26 22:17
 */
public interface QuestionDubboClient {
    Question getQuestionById(Long questionId);
    QuestionSubmit getQuestionSubmitById(Long questionId);
    boolean updateQuestionSubmitById(QuestionSubmit questionSubmit);
}

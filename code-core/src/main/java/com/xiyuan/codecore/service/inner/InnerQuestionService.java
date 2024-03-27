package com.xiyuan.codecore.service.inner;

import com.xiyuan.codecommon.model.entity.Question;
import com.xiyuan.codecommon.model.entity.QuestionSubmit;
import com.xiyuan.codecommon.service.QuestionDubboClient;
import com.xiyuan.codecore.mapper.QuestionMapper;
import com.xiyuan.codecore.mapper.QuestionSubmitMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author xiyuan
 * @description TODO
 * @date 2024/3/27 12:47
 */
@DubboService
public class InnerQuestionService implements QuestionDubboClient {
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private QuestionSubmitMapper questionSubmitMapper;
    @Override
    public Question getQuestionById(Long questionId) {
        return questionMapper.selectById(questionId);
    }

    @Override
    public QuestionSubmit getQuestionSubmitById(Long questionId) {
        return questionSubmitMapper.selectById(questionId);
    }

    @Override
    public boolean updateQuestionSubmitById(QuestionSubmit questionSubmit) {
        int rows = questionSubmitMapper.updateById(questionSubmit);
        return rows > 0;
    }
}

package com.xiyuan.codecore.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiyuan.codecore.model.dto.question.QuestionQueryRequest;
import com.xiyuan.codecore.model.entity.Question;
import com.xiyuan.codecore.model.vo.QuestionVO;

/**
 * @author xiyuan
 * @description 针对表【question】的数据库操作Service
 * @createDate 2024-01-16 13:38:52
 */
public interface QuestionService extends IService<Question> {
    /**
     * 校验
     *
     * @param question question
     */
    void validQuestion(Question question, boolean add);

    /**
     * 获取查询条件
     *
     * @param questionQueryRequest 查询请求参数
     * @return
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);

    /**
     * 分页获取接口封装
     *
     * @param questionPage page对象
     * @return
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage);

    /**
     * 获取接口封装
     *
     * @param question Entity
     * @return
     */
    QuestionVO getQuestionVO(Question question);
}

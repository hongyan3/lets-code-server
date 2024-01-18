package com.xiyuan.codecore.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiyuan.codecore.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.xiyuan.codecore.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.xiyuan.codecore.model.entity.QuestionSubmit;
import com.xiyuan.codecore.model.entity.User;
import com.xiyuan.codecore.model.vo.QuestionSubmitVO;

/**
 * @author xiyuan
 * @description 针对表【question_submit(题目提交表)】的数据库操作Service
 * @createDate 2024-01-16 13:33:11
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 校验
     *
     * @param question question
     */
    void validQuestionSubmit(QuestionSubmit question, boolean add);

    /**
     * 获取查询条件
     *
     * @param questionQueryRequest 查询请求参数
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionQueryRequest);

    /**
     * 分页获取接口封装
     *
     * @param questionPage page对象
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionPage, User loginUser);

    /**
     * 获取接口封装
     *
     * @param questionSubmit Entity
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    Long submitQuestion(QuestionSubmitAddRequest addRequest, User loginUser);
}

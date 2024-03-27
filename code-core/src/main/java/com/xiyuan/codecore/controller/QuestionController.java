package com.xiyuan.codecore.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiyuan.codecommon.common.BaseResponse;
import com.xiyuan.codecommon.common.ErrorCode;
import com.xiyuan.codecommon.common.ResultUtils;
import com.xiyuan.codecommon.exception.BusinessException;
import com.xiyuan.codecommon.exception.ThrowUtils;
import com.xiyuan.codecommon.model.dto.question.QuestionQueryRequest;
import com.xiyuan.codecommon.model.entity.Question;
import com.xiyuan.codecommon.model.vo.QuestionVO;
import com.xiyuan.codecore.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Resource
    private QuestionService questionService;

    @GetMapping
    public BaseResponse<Page<QuestionVO>> getQuestionList(@ModelAttribute QuestionQueryRequest queryRequest, HttpServletRequest request) {
        if (queryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = queryRequest.getCurrent();
        long size = queryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 100, ErrorCode.PARAMS_ERROR);
        Page<Question> page = questionService.page(new Page<>(current, size), questionService.getQueryWrapper(queryRequest));
        Page<QuestionVO> questionVOPage = questionService.getQuestionVOPage(page);
        return ResultUtils.success(questionVOPage);
    }

    @GetMapping("/{questionId}")
    public BaseResponse<QuestionVO> getQuestionById(@PathVariable Long questionId) {
        if (questionId == null || questionId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        QuestionVO questionVO = questionService.getQuestionVO(question);
        return ResultUtils.success(questionVO);
    }
}

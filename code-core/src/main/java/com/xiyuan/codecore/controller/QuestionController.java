package com.xiyuan.codecore.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiyuan.codecore.common.BaseResponse;
import com.xiyuan.codecore.common.ErrorCode;
import com.xiyuan.codecore.common.ResultUtils;
import com.xiyuan.codecore.exception.BusinessException;
import com.xiyuan.codecore.exception.ThrowUtils;
import com.xiyuan.codecore.model.dto.question.QuestionQueryRequest;
import com.xiyuan.codecore.model.entity.Question;
import com.xiyuan.codecore.model.vo.QuestionVO;
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
        QuestionVO questionVO = questionService.getQuestionVO(question);
        return ResultUtils.success(questionVO);
    }
}

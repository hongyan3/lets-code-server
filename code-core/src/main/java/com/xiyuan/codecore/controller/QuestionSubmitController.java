package com.xiyuan.codecore.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiyuan.codecommon.common.BaseResponse;
import com.xiyuan.codecommon.common.ResultUtils;
import com.xiyuan.codecommon.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.xiyuan.codecommon.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.xiyuan.codecommon.model.entity.QuestionSubmit;
import com.xiyuan.codecommon.model.entity.User;
import com.xiyuan.codecommon.model.vo.QuestionSubmitVO;
import com.xiyuan.codecore.service.QuestionSubmitService;
import com.xiyuan.codecore.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/question_submit")
public class QuestionSubmitController {
    @Resource
    private QuestionSubmitService questionSubmitService;
    @Resource
    private UserService userService;

    @PostMapping
    public BaseResponse<Long> submitQuestion(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        Long result = questionSubmitService.submitQuestion(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(result);
    }

    @GetMapping
    public BaseResponse<Page<QuestionSubmitVO>> getQuestionSubmitList(@ModelAttribute QuestionSubmitQueryRequest queryRequest, HttpServletRequest request) {
        long current = queryRequest.getCurrent();
        long pageSize = queryRequest.getPageSize();
        User loginUser = userService.getLoginUser(request);
        Page<QuestionSubmit> page = questionSubmitService.page(new Page<>(current, pageSize), questionSubmitService.getQueryWrapper(queryRequest));
        Page<QuestionSubmitVO> questionSubmitVOPage = questionSubmitService.getQuestionSubmitVOPage(page, loginUser);
        return ResultUtils.success(questionSubmitVOPage);
    }
}

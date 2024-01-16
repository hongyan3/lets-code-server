package com.xiyuan.codecore.controller;

import com.xiyuan.codecore.common.BaseResponse;
import com.xiyuan.codecore.common.ResultUtils;
import com.xiyuan.codecore.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.xiyuan.codecore.model.entity.User;
import com.xiyuan.codecore.service.QuestionSubmitService;
import com.xiyuan.codecore.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

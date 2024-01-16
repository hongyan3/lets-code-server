package com.xiyuan.codecore.controller;

import com.xiyuan.codecore.common.BaseResponse;
import com.xiyuan.codecore.model.dto.questionsubmit.QuestionSubmitAddQueryRequest;
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
    public BaseResponse<Long> submitQuestion(@RequestBody QuestionSubmitAddQueryRequest questionSubmitAddRequest, HttpServletRequest request) {
        return null;
    }
}

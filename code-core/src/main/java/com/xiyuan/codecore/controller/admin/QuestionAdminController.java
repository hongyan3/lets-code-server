package com.xiyuan.codecore.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiyuan.codecore.annotation.AuthCheck;
import com.xiyuan.codecore.common.BaseResponse;
import com.xiyuan.codecore.common.ErrorCode;
import com.xiyuan.codecore.common.ResultUtils;
import com.xiyuan.codecore.exception.BusinessException;
import com.xiyuan.codecore.exception.ThrowUtils;
import com.xiyuan.codecore.model.dto.question.QuestionAddRequest;
import com.xiyuan.codecore.model.dto.question.QuestionQueryRequest;
import com.xiyuan.codecore.model.dto.question.QuestionUpdateRequest;
import com.xiyuan.codecore.model.entity.Question;
import com.xiyuan.codecore.model.entity.User;
import com.xiyuan.codecore.model.enums.UserRoleEnum;
import com.xiyuan.codecore.service.QuestionService;
import com.xiyuan.codecore.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin/question")
public class QuestionAdminController {
    @Resource
    private QuestionService questionService;
    @Resource
    private UserService userService;

    /**
     * 获取问题列表
     *
     * @param queryRequest
     * @param request
     * @return 分页对象
     */
    @GetMapping
    @AuthCheck(AccessRole = UserRoleEnum.ADMIN)
    public BaseResponse<Page<Question>> getQuestionList(@ModelAttribute QuestionQueryRequest queryRequest, HttpServletRequest request) {
        if (queryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = queryRequest.getCurrent();
        long size = queryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 100, ErrorCode.PARAMS_ERROR);
        Page<Question> page = questionService.page(new Page<>(current, size), questionService.getQueryWrapper(queryRequest));
        return ResultUtils.success(page);
    }

    /**
     * 根据id获取问题
     *
     * @param questionId 路径参数id
     * @return question
     */
    @GetMapping("/{questionId}")
    @AuthCheck(AccessRole = UserRoleEnum.ADMIN)
    public BaseResponse<Question> getQuestionById(@PathVariable Long questionId) {
        if (questionId == null || questionId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = questionService.getById(questionId);
        return ResultUtils.success(question);
    }

    /**
     * 新增问题
     *
     * @param addRequest 新增问题请求
     * @param request    request
     * @return 问题id
     */
    @PostMapping
    @AuthCheck(AccessRole = UserRoleEnum.ADMIN)
    public BaseResponse<Long> addQuestion(@RequestBody QuestionAddRequest addRequest, HttpServletRequest request) {
        if (addRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = new Question();
        BeanUtils.copyProperties(addRequest, question);
        User loginUser = userService.getLoginUser(request);
        question.setUserId(loginUser.getId());
        boolean result = questionService.save(question);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(question.getId());
    }

    /**
     * 删除问题
     *
     * @param questionId 路径参数id
     * @return 布尔值
     */
    @DeleteMapping("/{questionId}")
    @AuthCheck(AccessRole = UserRoleEnum.ADMIN)
    public BaseResponse<Boolean> deleteQuestionById(@PathVariable Long questionId) {
        if (questionId == null || questionId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = questionService.removeById(questionId);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(result);
    }

    /**
     * 更新问题
     *
     * @param updateRequest
     * @return
     */
    @PutMapping
    @AuthCheck(AccessRole = UserRoleEnum.ADMIN)
    public BaseResponse<Boolean> updateQuestion(@RequestBody QuestionUpdateRequest updateRequest) {
        if (updateRequest == null || updateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = new Question();
        BeanUtils.copyProperties(updateRequest, question);
        questionService.validQuestion(question, false);
        Long id = updateRequest.getId();
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = questionService.updateById(question);
        return ResultUtils.success(result);
    }
}

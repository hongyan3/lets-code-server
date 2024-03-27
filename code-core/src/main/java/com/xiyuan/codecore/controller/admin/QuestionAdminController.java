package com.xiyuan.codecore.controller.admin;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiyuan.codecommon.model.JudgeCase;
import com.xiyuan.codecommon.model.JudgeConfig;
import com.xiyuan.codecommon.model.dto.question.QuestionAddRequest;
import com.xiyuan.codecommon.model.dto.question.QuestionQueryRequest;
import com.xiyuan.codecommon.model.dto.question.QuestionUpdateRequest;
import com.xiyuan.codecore.annotation.AuthCheck;
import com.xiyuan.codecommon.common.BaseResponse;
import com.xiyuan.codecommon.common.ErrorCode;
import com.xiyuan.codecommon.common.ResultUtils;
import com.xiyuan.codecommon.exception.BusinessException;
import com.xiyuan.codecommon.exception.ThrowUtils;
import com.xiyuan.codecommon.model.entity.Question;
import com.xiyuan.codecommon.model.entity.User;
import com.xiyuan.codecommon.model.enums.UserRoleEnum;
import com.xiyuan.codecore.service.QuestionService;
import com.xiyuan.codecore.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/admin/question")
public class QuestionAdminController {
    @Resource
    private QuestionService questionService;
    @Resource
    private UserService userService;

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
        // 处理参数转换
        Question question = new Question();
        BeanUtils.copyProperties(addRequest, question);
        List<String> tags = addRequest.getTags();
        List<JudgeCase> judgeCase = addRequest.getJudgeCase();
        JudgeConfig judgeConfig = addRequest.getJudgeConfig();
        if (tags != null) {
            question.setTags(JSONUtil.toJsonStr(tags));
        }
        if (judgeCase != null) {
            question.setJudgeCase(JSONUtil.toJsonStr(judgeCase));
        }
        if (judgeConfig != null) {
            question.setJudgeConfig(JSONUtil.toJsonStr(judgeConfig));
        }

        User loginUser = userService.getLoginUser(request);
        question.setUserId(loginUser.getId());
        questionService.validQuestion(question, true);
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
        List<String> tags = updateRequest.getTags();
        List<JudgeCase> judgeCase = updateRequest.getJudgeCase();
        JudgeConfig judgeConfig = updateRequest.getJudgeConfig();
        if (tags != null) {
            question.setTags(JSONUtil.toJsonStr(tags));
        }
        if (judgeCase != null) {
            question.setJudgeCase(JSONUtil.toJsonStr(judgeCase));
        }
        if (judgeConfig != null) {
            question.setJudgeConfig(JSONUtil.toJsonStr(judgeConfig));
        }

        questionService.validQuestion(question, false);
        Long id = updateRequest.getId();
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = questionService.updateById(question);
        return ResultUtils.success(result);
    }

    /**
     * 查询问题列表（仅管理员）
     *
     * @param queryRequest
     * @param request
     * @return
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
     * 根据ID查询问题（仅管理员）
     *
     * @param questionId
     * @return
     */
    @GetMapping("/{questionId}")
    @AuthCheck(AccessRole = UserRoleEnum.ADMIN)
    public BaseResponse<Question> getQuestionById(@PathVariable Long questionId) {
        if (questionId == null || questionId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(question);
    }
}

package com.xiyuan.codecore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiyuan.codecore.common.ErrorCode;
import com.xiyuan.codecore.constant.CommonConstant;
import com.xiyuan.codecore.exception.BusinessException;
import com.xiyuan.codecore.judge.service.JudgeService;
import com.xiyuan.codecore.mapper.QuestionSubmitMapper;
import com.xiyuan.codecore.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.xiyuan.codecore.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.xiyuan.codecore.model.entity.Question;
import com.xiyuan.codecore.model.entity.QuestionSubmit;
import com.xiyuan.codecore.model.entity.User;
import com.xiyuan.codecore.model.enums.QuestionSubmitLanguageEnum;
import com.xiyuan.codecore.model.enums.QuestionSubmitStatusEnum;
import com.xiyuan.codecore.model.enums.UserRoleEnum;
import com.xiyuan.codecore.model.vo.QuestionSubmitVO;
import com.xiyuan.codecore.model.vo.UserVO;
import com.xiyuan.codecore.service.QuestionService;
import com.xiyuan.codecore.service.QuestionSubmitService;
import com.xiyuan.codecore.service.UserService;
import com.xiyuan.codecore.utils.SqlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author xiyuan
 * @description 针对表【questionSubmit_submit(题目提交表)】的数据库操作Service实现
 * @createDate 2024-01-16 13:33:11
 */
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {

    @Resource
    private UserService userService;
    @Resource
    private QuestionService questionService;

    @Resource
    @Lazy
    private JudgeService judgeService;

    @Override
    public void validQuestionSubmit(QuestionSubmit questionSubmit, boolean add) {
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = questionSubmit.getId();
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        Long questionId = questionSubmit.getQuestionId();
        Long userId = questionSubmit.getUserId();
        if (add) {
            if (StringUtils.isAnyBlank(language, code)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (!ObjectUtils.allNotNull(questionId, userId)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }

    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {

        if (questionSubmitQueryRequest == null) {
            return null;
        }
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();

        Long id = questionSubmitQueryRequest.getId();
        String language = questionSubmitQueryRequest.getLanguage();
        Integer status = questionSubmitQueryRequest.getStatus();
        Long questionId = questionSubmitQueryRequest.getQuestionId();
        Long userId = questionSubmitQueryRequest.getUserId();
        String sortField = questionSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();

        queryWrapper.eq(ObjectUtils.allNotNull(id), "id", id);
        queryWrapper.eq(ObjectUtils.allNotNull(language), "language", language);
        queryWrapper.eq(ObjectUtils.allNotNull(status), "status", status);
        queryWrapper.eq(ObjectUtils.allNotNull(questionId), "question_id", questionId);
        queryWrapper.eq(ObjectUtils.allNotNull(userId), "user_id", userId);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return queryWrapper;
    }

    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser) {
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>();
        if (CollectionUtils.isEmpty(questionSubmitPage.getRecords())) {
            return questionSubmitVOPage;
        }
        List<QuestionSubmitVO> list = questionSubmitPage.getRecords().stream().map(e -> getQuestionSubmitVO(e, loginUser)).collect(Collectors.toList());
        questionSubmitVOPage.setRecords(list);
        questionSubmitVOPage.setTotal(questionSubmitPage.getTotal());
        return questionSubmitVOPage;
    }

    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser) {
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
        // 1. 关联查询用户信息
        long userId = questionSubmitVO.getUserId();
        User user = userService.getById(userId);
        UserVO userVO = userService.getUserVO(user);
        questionSubmitVO.setUserInfo(userVO);
        // 2. 判断用户权限
        if (loginUser.getId() != userId && loginUser.getRole() != UserRoleEnum.ADMIN.getValue()) {
            questionSubmitVO.setCode(null);
        }
        return questionSubmitVO;
    }

    @Override
    public Long submitQuestion(QuestionSubmitAddRequest addRequest, User loginUser) {
        // 校验语言类型是否合法
        String language = addRequest.getLanguage();
        if (QuestionSubmitLanguageEnum.getEnumByValue(language) == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "语言类型错误");
        }
        Long questionId = addRequest.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        Long userId = loginUser.getId();
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setLanguage(addRequest.getLanguage());
        questionSubmit.setCode(addRequest.getCode());
        // 设置初始状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean result = this.save(questionSubmit);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        // todo 执行判题服务
        CompletableFuture.runAsync(() -> {
            System.out.println("开始执行");
            judgeService.doJudge(questionSubmit.getId());
        });
        return questionSubmit.getId();
    }
}





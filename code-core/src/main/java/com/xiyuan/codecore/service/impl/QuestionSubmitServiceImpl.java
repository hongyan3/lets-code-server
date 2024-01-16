package com.xiyuan.codecore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiyuan.codecore.common.ErrorCode;
import com.xiyuan.codecore.constant.CommonConstant;
import com.xiyuan.codecore.exception.BusinessException;
import com.xiyuan.codecore.mapper.QuestionSubmitMapper;
import com.xiyuan.codecore.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.xiyuan.codecore.model.entity.QuestionSubmit;
import com.xiyuan.codecore.model.vo.QuestionSubmitVO;
import com.xiyuan.codecore.service.QuestionSubmitService;
import com.xiyuan.codecore.utils.SqlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiyuan
 * @description 针对表【questionSubmit_submit(题目提交表)】的数据库操作Service实现
 * @createDate 2024-01-16 13:33:11
 */
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {
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
            if (StringUtils.isAnyBlank(language, code) || id != null) {
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
        String judgeInfo = questionSubmitQueryRequest.getJudgeInfo();
        Integer status = questionSubmitQueryRequest.getStatus();
        Long questionId = questionSubmitQueryRequest.getQuestionId();
        Long userId = questionSubmitQueryRequest.getUserId();
        String sortField = questionSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();

        queryWrapper.eq(ObjectUtils.allNotNull(id), "id", id);
        queryWrapper.eq(ObjectUtils.allNotNull(language), "language", language);
        queryWrapper.eq(ObjectUtils.allNotNull(judgeInfo), "tags", judgeInfo);
        queryWrapper.eq(ObjectUtils.allNotNull(status), "status", status);
        queryWrapper.eq(ObjectUtils.allNotNull(questionId), "question_id", questionId);
        queryWrapper.eq(ObjectUtils.allNotNull(userId), "user_id", userId);
        queryWrapper.eq("is_delete", 1);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return queryWrapper;
    }

    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage) {
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>();
        if (CollectionUtils.isEmpty(questionSubmitPage.getRecords())) {
            return questionSubmitVOPage;
        }
        List<QuestionSubmitVO> list = questionSubmitPage.getRecords().stream().map(this::getQuestionSubmitVO).collect(Collectors.toList());
        questionSubmitVOPage.setRecords(list);
        questionSubmitVOPage.setTotal(questionSubmitPage.getTotal());
        return questionSubmitVOPage;
    }

    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit) {
        QuestionSubmitVO questionSubmitVO = new QuestionSubmitVO();
        BeanUtils.copyProperties(questionSubmit, questionSubmitVO);
        return questionSubmitVO;
    }
}





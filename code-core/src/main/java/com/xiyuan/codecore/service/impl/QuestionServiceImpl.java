package com.xiyuan.codecore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiyuan.codecore.common.ErrorCode;
import com.xiyuan.codecore.constant.CommonConstant;
import com.xiyuan.codecore.exception.BusinessException;
import com.xiyuan.codecore.mapper.QuestionMapper;
import com.xiyuan.codecore.model.dto.question.QuestionQueryRequest;
import com.xiyuan.codecore.model.entity.Question;
import com.xiyuan.codecore.model.vo.QuestionVO;
import com.xiyuan.codecore.service.QuestionService;
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
 * @description 针对表【question】的数据库操作Service实现
 * @createDate 2024-01-16 13:38:52
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
        implements QuestionService {

    @Override
    public void validQuestion(Question question, boolean add) {
        if (question == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String title = question.getTitle();
        Long id = question.getId();
        if (add) {
            if (StringUtils.isAnyBlank(title) || id != null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
    }

    @Override
    public QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest) {
        if (questionQueryRequest == null) {
            return null;
        }
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        Long id = questionQueryRequest.getId();
        String title = questionQueryRequest.getTitle();
        String description = questionQueryRequest.getDescription();
        String tags = questionQueryRequest.getTags();
        Long userId = questionQueryRequest.getUserId();
        String sortField = questionQueryRequest.getSortField();
        String sortOrder = questionQueryRequest.getSortOrder();
        // 拼接查询Like条件
        if (StringUtils.isAnyBlank(title, description)) {
            queryWrapper.like(ObjectUtils.allNotNull(title), "title", title)
                    .like(ObjectUtils.allNotNull(description), "description", description);
        }
        queryWrapper.eq(ObjectUtils.allNotNull(id), "id", id);
        queryWrapper.eq(ObjectUtils.allNotNull(description), "description", description);
        queryWrapper.eq(ObjectUtils.allNotNull(tags), "tags", tags);
        queryWrapper.eq(ObjectUtils.allNotNull(id), "user_id", userId);
        queryWrapper.eq("is_delete", 1);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        return queryWrapper;
    }

    @Override
    public Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage) {
        Page<QuestionVO> questionVOPage = new Page<>();
        if (CollectionUtils.isEmpty(questionPage.getRecords())) {
            return questionVOPage;
        }
        List<QuestionVO> list = questionPage.getRecords().stream().map(this::getQuestionVO).collect(Collectors.toList());
        questionVOPage.setRecords(list);
        questionVOPage.setTotal(questionPage.getTotal());
        return questionVOPage;
    }

    @Override
    public QuestionVO getQuestionVO(Question question) {
        QuestionVO questionVO = new QuestionVO();
        BeanUtils.copyProperties(question, questionVO);
        return questionVO;
    }
}





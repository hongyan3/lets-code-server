package com.xiyuan.codecore.model.vo;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiyuan.codecommon.model.JudgeInfo;
import com.xiyuan.codecore.model.entity.QuestionSubmit;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目提交表
 *
 * @TableName question_submit
 */
@TableName(value = "question_submit")
@Data
public class QuestionSubmitVO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 题目ID
     */
    private Long id;
    /**
     * 编程语言
     */
    private String language;
    /**
     * 提交代码
     */
    private String code;
    /**
     * 判题信息 Json
     */
    private JudgeInfo judgeInfo;
    /**
     * 判题状态 0-待判题 1-判题中 2-成功 3-失败
     */
    private Integer status;
    /**
     * 题目ID
     */
    private Long questionId;
    /**
     * 创建者ID
     */
    private Long userId;
    /**
     * 用户信息
     */
    private UserVO userInfo;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    public static QuestionSubmitVO objToVo(QuestionSubmit questionSubmit) {
        if (questionSubmit == null) {
            return null;
        }
        QuestionSubmitVO questionSubmitVO = new QuestionSubmitVO();
        BeanUtils.copyProperties(questionSubmit, questionSubmitVO);
        String judgeInfoStr = questionSubmit.getJudgeInfo();
        if (judgeInfoStr != null) {
            questionSubmitVO.setJudgeInfo(JSONUtil.toBean(judgeInfoStr, JudgeInfo.class));
        }
        return questionSubmitVO;
    }

    public static QuestionSubmit VoToObj(QuestionSubmitVO questionSubmitVO) {
        if (questionSubmitVO == null) {
            return null;
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        BeanUtils.copyProperties(questionSubmitVO, questionSubmit);
        JudgeInfo judgeInfo = questionSubmitVO.getJudgeInfo();
        if (judgeInfo != null) {
            questionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        }
        return questionSubmit;
    }
}
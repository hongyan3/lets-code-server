package com.xiyuan.codecommon.model.dto.question;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiyuan.codecommon.model.JudgeCase;
import com.xiyuan.codecommon.model.JudgeConfig;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @TableName question
 */
@TableName(value = "question")
@Data
public class QuestionUpdateRequest implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 题目ID
     */
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 问题描述
     */
    private String description;
    /**
     * 标签 Json数组
     */
    private List<String> tags;
    /**
     * 标准答案
     */
    private String answer;
    /**
     * 判题用例 Json数组
     */
    private List<JudgeCase> judgeCase;
    /**
     * 判题配置 Json
     */
    private JudgeConfig judgeConfig;
}
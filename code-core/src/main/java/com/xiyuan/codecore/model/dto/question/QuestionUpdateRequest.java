package com.xiyuan.codecore.model.dto.question;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

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
    private String tags;
    /**
     * 标准答案
     */
    private String answer;
    /**
     * 判题用例 Json数组
     */
    private String judgeCase;
    /**
     * 判题配置 Json
     */
    private String judgeConfig;
}
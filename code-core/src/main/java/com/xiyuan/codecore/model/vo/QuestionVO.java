package com.xiyuan.codecore.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName question
 */
@TableName(value = "question")
@Data
public class QuestionVO implements Serializable {
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
     * 总提交数
     */
    private Integer submitNum;
    /**
     * 总通过数
     */
    private Integer accessNum;
    /**
     * 判题用例 Json数组
     */
    private String judgeCase;
    /**
     * 判题配置 Json
     */
    private String judgeConfig;
    /**
     * 创建者ID
     */
    private Long userId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
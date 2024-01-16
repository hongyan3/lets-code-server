package com.xiyuan.codecore.model.dto.question;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiyuan.codecore.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName question
 */
@TableName(value = "question")
@Data
public class QuestionQueryRequest extends PageRequest implements Serializable {
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
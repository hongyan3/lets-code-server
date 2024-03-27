package com.xiyuan.codecommon.model.dto.questionsubmit;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 题目提交表
 *
 * @TableName question_submit
 */
@TableName(value = "question_submit")
@Data
public class QuestionSubmitAddRequest implements Serializable {
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
     * 题目ID
     */
    private Long questionId;
}
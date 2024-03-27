package com.xiyuan.codecommon.model.dto.questionsubmit;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiyuan.codecommon.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 题目提交表
 *
 * @TableName question_submit
 */
@TableName(value = "question_submit")
@Data
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {
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
}
package com.xiyuan.codecore.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目提交表
 *
 * @TableName question_submit
 */
@TableName(value = "question_submit")
@Data
public class QuestionSubmit implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 题目ID
     */
    @TableId(type = IdType.AUTO)
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
    private String judgeInfo;
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
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
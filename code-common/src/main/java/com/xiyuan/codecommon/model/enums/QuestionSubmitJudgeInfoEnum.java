package com.xiyuan.codecommon.model.enums;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 提交信息枚举
 */
@Getter
public enum QuestionSubmitJudgeInfoEnum {
    ACCEPTED("成功", "Accepted"),
    WRONG_ANSWER("答案错误", "Wrong Answer"),
    WAITING("等待中", "Waiting"),
    TIME_LIMIT_EXCEEDED("时间超出限制", "Time Out"),
    MEMORY_LIMIT_EXCEEDED("内存超出限制", "Memory Out");

    private final String text;
    private final String value;

    QuestionSubmitJudgeInfoEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static QuestionSubmitJudgeInfoEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (QuestionSubmitJudgeInfoEnum anEnum : QuestionSubmitJudgeInfoEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}

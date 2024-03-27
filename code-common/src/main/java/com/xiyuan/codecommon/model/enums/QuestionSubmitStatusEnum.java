package com.xiyuan.codecommon.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 题目提交状态枚举
 */
@Getter
public enum QuestionSubmitStatusEnum {
    WAITING("等待中", 0),

    RUNNING("管理员", 1),

    SUCCESS("管理员", 2),

    FAILED("管理员", 3);

    private final String text;
    private final Integer value;

    QuestionSubmitStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据roleCode获取枚举
     */
    public static QuestionSubmitStatusEnum getEnumByStatusCode(Integer statusCode) {
        if (statusCode == null) {
            return null;
        }
        for (QuestionSubmitStatusEnum roleEnum : QuestionSubmitStatusEnum.values()) {
            if (roleEnum.value == statusCode) {
                return roleEnum;
            }
        }
        return null;
    }

    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

}

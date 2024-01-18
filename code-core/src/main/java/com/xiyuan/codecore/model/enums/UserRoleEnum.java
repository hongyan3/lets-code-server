package com.xiyuan.codecore.model.enums;

import lombok.Getter;

/**
 * 用户角色枚举
 */
@Getter
public enum UserRoleEnum {
    USER("普通用户", 1),

    ADMIN("管理员", 2);

    private final String text;
    private final int value;

    UserRoleEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据roleCode获取枚举
     */
    public static UserRoleEnum getEnumByRoleCode(Integer roleCode) {
        if (roleCode == null) {
            return null;
        }
        for (UserRoleEnum roleEnum : UserRoleEnum.values()) {
            if (roleEnum.value == roleCode) {
                return roleEnum;
            }
        }
        return null;
    }
}

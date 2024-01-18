package com.xiyuan.codecore.judge.codesandbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeRequest {
    /**
     * 输入参数列表
     */
    private List<String> inputList;
    /**
     * 待执行代码
     */
    private String code;
    /**
     * 代码语言
     */
    private String language;
}

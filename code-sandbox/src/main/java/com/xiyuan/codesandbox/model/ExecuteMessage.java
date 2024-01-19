package com.xiyuan.codesandbox.model;

import lombok.Data;

/**
 * 进程执行信息
 */
@Data
public class ExecuteMessage {
    private Integer exitCode;
    private String message;
    private String errMessage;
}

package com.xiyuan.codecore.judge.codesandbox;

import com.xiyuan.codecore.judge.codesandbox.impl.ExampleCodeSandBox;
import com.xiyuan.codecore.judge.codesandbox.impl.RemoteCodeSandBox;

/**
 * 代码沙箱工厂 根据字符串参数创建指定的代码沙箱示例
 */
public class CodeSandBoxFactory {
    public static CodeSandBox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandBox();
            case "remote":
                return new RemoteCodeSandBox();
            default:
                return new RemoteCodeSandBox();
        }
    }
}

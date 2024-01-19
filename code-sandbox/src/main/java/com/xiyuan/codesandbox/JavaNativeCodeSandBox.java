package com.xiyuan.codesandbox;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import com.xiyuan.codecommon.model.ExecuteCodeRequest;
import com.xiyuan.codecommon.model.ExecuteCodeResponse;
import com.xiyuan.codesandbox.model.ExecuteMessage;
import com.xiyuan.codesandbox.utils.ProcessUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class JavaNativeCodeSandBox implements CodeSandBox {
    private static final String GLOBAL_CODE_DIR_NAME = "code";
    private static final String GLOBAL_JAVA_CLASS_NAME = "Main.java";

    public static void main(String[] args) {
        JavaNativeCodeSandBox javaNativeCodeSandBox = new JavaNativeCodeSandBox();
        String code = ResourceUtil.readUtf8Str("Main.java");
        ExecuteCodeRequest request = ExecuteCodeRequest.builder()
                .language("Java").inputList(Arrays.asList("1 2", "1 3")).code(code).build();
        javaNativeCodeSandBox.executeCode(request);
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        String code = executeCodeRequest.getCode();
        String language = executeCodeRequest.getLanguage();

        // 判断全局代码目录是否存在，没有则新建
        String userDir = System.getProperty("user.dir");
        String codePathName = userDir + File.separator + GLOBAL_CODE_DIR_NAME;
        if (!FileUtil.exist(codePathName)) {
            FileUtil.mkdir(codePathName);
        }

        // 把用户代码隔离存放
        String userCodePath = codePathName + File.separator + UUID.randomUUID();
        String userCodeFilePath = userCodePath + File.separator + GLOBAL_JAVA_CLASS_NAME;
        File userCodeFile = FileUtil.writeString(code, userCodeFilePath, StandardCharsets.UTF_8);

        // 编译文件
        String compileCmd = String.format("javac -encoding utf-8 %s", userCodeFile.getAbsolutePath());
        try {
            Process compileProcess = Runtime.getRuntime().exec(compileCmd);
            ExecuteMessage executeMessage = ProcessUtils.runProcess(compileProcess, "编译");
            System.out.println(executeMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 执行文件
        List<ExecuteMessage> executeMessagesList = new ArrayList<>();
        for (String inputArgs : inputList) {
            String runCmd = String.format("java -Dfile.encoding=UTF-8 -cp %s Main %s", userCodePath, inputArgs);
            try {
                Process execProcess = Runtime.getRuntime().exec(runCmd);
                ExecuteMessage executeMessage = ProcessUtils.runProcess(execProcess, "运行");
                System.out.println(executeMessage);
                executeMessagesList.add(executeMessage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        // 收集整理输出结果
        ExecuteCodeResponse response = new ExecuteCodeResponse();
        List<String> outputList = new ArrayList<>();
        for (ExecuteMessage message: executeMessagesList) {
            String errMsg = message.getErrMessage();
            if (StringUtils.isNoneEmpty(errMsg)) {
                outputList.add(errMsg);
                response.setStatus(3);
                break;
            }
            outputList.add(message.getMessage());
        }
        response.setStatus(1);

        return null;
    }
}

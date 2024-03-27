package com.xiyuan.codesandbox.utils;

import cn.hutool.core.date.StopWatch;
import com.xiyuan.codesandbox.model.ExecuteMessage;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ProcessUtils {
    /**
     * @param runProcess
     * @param option
     * @return
     */
    public static ExecuteMessage runProcess(Process runProcess, String option) {
        ExecuteMessage executeMessage = new ExecuteMessage();
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            int exitCode = runProcess.waitFor();
            stopWatch.stop();
            executeMessage.setTime(stopWatch.getTotalTimeMillis());
            executeMessage.setExitCode(exitCode);
            if (exitCode == 0) {
                System.out.println(option + "成功");
                BufferedReader compileBuf = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                StringBuilder compileOutputStringBuilder = new StringBuilder();
                // 逐行读取
                String compileOutputLine;
                while ((compileOutputLine = compileBuf.readLine()) != null) {
                    compileOutputStringBuilder.append(compileOutputLine);
                }
                executeMessage.setMessage(compileOutputStringBuilder.toString());
            } else {
                System.out.println(option + "失败");
                BufferedReader compileBuf = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                StringBuilder errCompileOutputStringBuilder = new StringBuilder();
                // 逐行读取
                String compileOutputLine;
                while ((compileOutputLine = compileBuf.readLine()) != null) {
                    errCompileOutputStringBuilder.append(compileOutputLine);
                }
                executeMessage.setErrMessage(errCompileOutputStringBuilder.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return executeMessage;
    }
}

package com.xiyuan.codejudge.consumer;

import com.xiyuan.codejudge.service.JudgeService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xiyuan
 * @description TODO
 * @date 2024/3/26 20:58
 */
@Component
public class codeJudgeConsumer {
    @Resource
    private JudgeService judgeService;
    @RabbitListener(queues = "code_queue")
    public void listenCodeQueueMessage(String message) {
        long id = Long.parseLong(message);
        judgeService.doJudge(id);
    }
}

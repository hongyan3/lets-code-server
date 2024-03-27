package com.xiyuan.codecore.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xiyuan
 * @description TODO
 * @date 2024/3/26 20:51
 */
@Component
public class CodeJudgeProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;
    public void sendMessage(String queueName,String message) {
        rabbitTemplate.convertAndSend(queueName,message);
    }
}

package com.meizhi.rabbitmq.topic.controller;


import com.meizhi.rabbitmq.config.RmConst;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class LogController {


    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 模拟发送info类型日志
     */
    @RequestMapping("/info")
    public void sendInfoLog(String msg) {
        this.rabbitTemplate.convertAndSend(RmConst.EXCHANGE_TOPIC_LOG, "log.info.xxx", msg);
    }


    /**
     * 模拟发送error类型日志
     */
    @RequestMapping("/error")
    public void sendErrorLog(String msg) {
        this.rabbitTemplate.convertAndSend(RmConst.EXCHANGE_TOPIC_LOG, "log.error.xxx", msg);
    }


    /**
     * 模拟发送warning类型日志
     */
    @RequestMapping("/warning")
    public void sendWarningLog(String msg) {
        this.rabbitTemplate.convertAndSend(RmConst.EXCHANGE_TOPIC_LOG, "log.warning.xxx", msg);
    }

    /**
     * 模拟发送无法路由的消息
     */
    @RequestMapping("/other")
    public void sendNoRoutingLog(String msg) {
        this.rabbitTemplate.convertAndSend(RmConst.EXCHANGE_TOPIC_LOG, "log.other", msg);
    }




}

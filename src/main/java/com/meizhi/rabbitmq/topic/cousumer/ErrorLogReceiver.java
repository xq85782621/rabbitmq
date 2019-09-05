package com.meizhi.rabbitmq.topic.cousumer;

import com.meizhi.rabbitmq.config.RmConst;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = RmConst.QUEUE_LOG_ERROR)
public class ErrorLogReceiver {

    @RabbitHandler
    public void process(String msg) {
        System.err.println("我是是error日志的消费者,我消费了该信息: " + msg);
    }

}
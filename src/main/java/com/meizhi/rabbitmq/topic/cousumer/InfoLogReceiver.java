package com.meizhi.rabbitmq.topic.cousumer;


import com.meizhi.rabbitmq.config.RmConst;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 该类用来消费队列里的信息,可以在这里进行自己的业务逻辑处理
 */

@Component
@RabbitListener(queues = RmConst.QUEUE_LOG_INFO)
public class InfoLogReceiver {

    @RabbitHandler
    public void process(String msg) {
        System.err.println("我是是info日志的消费者,我消费了该信息: " + msg);
    }

}

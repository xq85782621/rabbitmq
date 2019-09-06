package com.meizhi.rabbitmq.topic.cousumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * 类说明：
 */
@Component
public class WarningLogReceiver implements ChannelAwareMessageListener {


    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            String msg = new String(message.getBody());
            System.out.println("warningLog>>>>>>>接收到消息:" + msg);
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(),
                        false);

                System.out.println("warningLog>>>>>>消息已消费");

            } catch (Exception e) {
                System.out.println(e.getMessage());
                // basicReject一次只能拒绝接收一个消息
                // basicNack方法可以支持一次0个或多个消息的拒收
                // requeue 是否重新投递,false的话 该消息直接就被rabbitmq丢弃了,
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                System.out.println("warningLog>>>>>>消费出现异常,派发给死信交换器");
                throw e;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
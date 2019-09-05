package com.meizhi.rabbitmq.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {


    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setPublisherConfirms(true);   //发送者确认
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate newRabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setMandatory(true);
        template.setConfirmCallback(confirmCallback());
        template.setReturnCallback(returnCallback());
        return template;
    }

    //==============================================================================================
    /**
     * 声明日志exchange
     */
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(RmConst.EXCHANGE_TOPIC_LOG);
    }


    //==============================================================================================
    /**
     * 声明日志 Queue
     */
    @Bean
    public Queue queueLogInfo() {
        return new Queue(RmConst.QUEUE_LOG_INFO);
    }

    @Bean
    public Queue queueLogError() {
        return new Queue(RmConst.QUEUE_LOG_ERROR);
    }

    @Bean
    public Queue queueLogWarning() {
        return new Queue(RmConst.QUEUE_LOG_WARNING);
    }

    //==============================================================================================

    /**
     * queue通过routingKey绑定到exchange
     */
    @Bean
    public Binding bindingQueueLogInfo() {
        return BindingBuilder
                .bind(queueLogInfo())
                .to(exchange())
                .with(RmConst.LOG_INFO_KEY);
    }
    @Bean
    public Binding bindingQueueLogError() {
        return BindingBuilder
                .bind(queueLogError())
                .to(exchange())
                .with(RmConst.LOG_ERROR_KEY);
    }
    @Bean
    public Binding bindingQueueLogWarning() {
        return BindingBuilder
                .bind(queueLogWarning())
                .to(exchange())
                .with(RmConst.LOG_WARNING_KEY);
    }

    //==============================================================================================















    //===============生产者发送确认==========
    @Bean
    public RabbitTemplate.ConfirmCallback confirmCallback() {
        return new RabbitTemplate.ConfirmCallback() {

            @Override
            public void confirm(CorrelationData correlationData,
                                boolean ack, String cause) {
                if (ack) {
                    System.err.println("发送者确认发送给mq成功");
                } else {
                    //处理失败的消息
                    System.err.println("发送者发送给mq失败,考虑重发:" + cause);
                }
            }
        };
    }

    @Bean
    public RabbitTemplate.ReturnCallback returnCallback() {
        return new RabbitTemplate.ReturnCallback() {

            @Override
            public void returnedMessage(Message message,
                                        int replyCode,
                                        String replyText,
                                        String exchange,
                                        String routingKey) {
                System.err.println("无法路由的消息，需要考虑另外处理。");
                System.err.println("Returned replyText：" + replyText);
                System.err.println("Returned exchange：" + exchange);
                System.err.println("Returned routingKey：" + routingKey);
                String msgJson = new String(message.getBody());
                System.err.println("Returned Message：" + msgJson);
            }
        };
    }

}
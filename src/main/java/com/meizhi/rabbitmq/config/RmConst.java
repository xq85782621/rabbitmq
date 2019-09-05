package com.meizhi.rabbitmq.config;


/**
 * queue和exchange以及routingKey名称统一定义
 */
public class RmConst {


    /**
     * 一个处理日志的交换器上,挂载三个队列
     * 每个队列分别处理不同级别的日志
     */
    //交换器
    public final static String EXCHANGE_TOPIC_LOG = "log_exchange";

    //队列的名字
    public final static String QUEUE_LOG_INFO = "log.info";
    public final static String QUEUE_LOG_ERROR = "log.error";
    public final static String QUEUE_LOG_WARNING = "log.warning";

    //队列和交换机绑定时的路由键
    public final static String LOG_INFO_KEY = "log.info.*";
    public final static String LOG_ERROR_KEY = "log.error.*";
    public final static String LOG_WARNING_KEY = "log.warning.*";




}

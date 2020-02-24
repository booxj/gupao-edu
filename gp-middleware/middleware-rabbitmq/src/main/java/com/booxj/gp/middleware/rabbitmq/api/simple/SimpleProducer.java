package com.booxj.gp.middleware.rabbitmq.api.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class SimpleProducer {

    private final static String EXCHANGE_NAME = "SIMPLE_EXCHANGE";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("47.110.42.93");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("admin");
        factory.setPassword("admin");

        // 建立连接
        Connection conn = factory.newConnection();
        // 创建消息通道
        Channel channel = conn.createChannel();

        // 发送消息
        String msg = "Hello RabbitMQ";

        /**
         * EXCHANGE_NAME        交换机
         * booxj                路由
         * null                 属性
         * msg.getBytes()       消息字节
         */
        channel.basicPublish(EXCHANGE_NAME, "booxj", null, msg.getBytes());

        channel.close();
        conn.close();

    }
}

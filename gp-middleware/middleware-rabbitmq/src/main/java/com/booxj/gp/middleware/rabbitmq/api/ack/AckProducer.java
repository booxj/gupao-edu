package com.booxj.gp.middleware.rabbitmq.api.ack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class AckProducer {

    private final static String QUEUE_NAME = "ACK_QUEUE";

    public static void main(String[] args) throws Exception {
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

        String msg = "test ack message ";
        // 声明队列（默认交换机AMQP default，Direct）
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 发送消息
        // String exchange, String routingKey, BasicProperties props, byte[] body
        for (int i = 0; i < 5; i++) {
            channel.basicPublish("", QUEUE_NAME, null, (msg + i).getBytes());
        }

        channel.close();
        conn.close();
    }

}

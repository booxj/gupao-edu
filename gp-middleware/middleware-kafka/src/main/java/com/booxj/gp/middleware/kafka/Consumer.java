package com.booxj.gp.middleware.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

public class Consumer extends Thread {

    private final KafkaConsumer kafkaConsumer;


    public Consumer(String topic) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "47.110.42.93:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "GP-Kafka-Consumer");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        kafkaConsumer = new KafkaConsumer(properties);
        kafkaConsumer.subscribe(Collections.singletonList(topic));

    }

    @Override
    public void run() {
        while (true) {
            ConsumerRecords<Integer, String> consumerRecords = kafkaConsumer.poll(1000);
            for (ConsumerRecord<Integer, String> record : consumerRecords) {
                System.out.println("message receive:" + record.value());
//                kafkaConsumer.commitAsync();
            }
        }
    }

    public static void main(String[] args) {
        new Consumer("test").start();
    }
}

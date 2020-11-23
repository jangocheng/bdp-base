package com.platform.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Optional;

/**
 * author: wlhbdp
 * create: 2020-05-20 10:09
 */

public class ModelConsumer {

    /**
     * 监听模型的变化，当发现模型变化后，更新内存中的模型
     * @param record Kafka Records
     */
    @KafkaListener(topics = "${app-config.topics.notification}")
    public void modelUpdateListener(ConsumerRecord<String, String> record) {
        Optional<String> wrapperMessage = Optional.ofNullable(record.value());
        if (wrapperMessage.isPresent()) {
            String message = wrapperMessage.get();

        }
    }
}

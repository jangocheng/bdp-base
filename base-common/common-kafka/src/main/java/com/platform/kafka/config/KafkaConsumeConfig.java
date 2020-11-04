package com.platform.kafka.config;

import com.platform.kafka.condition.ConsumeCondition;
import com.platform.kafka.properties.KafkaProperties;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wulinhao
 * @ClassName: KafkaConsumeConfig
 * @Description: kafka消费
 * @date 2020/7/9上午11:31
 *
 */

@Configuration
@Conditional(ConsumeCondition.class)
@EnableConfigurationProperties(KafkaProperties.class)
@EnableKafka
@Log4j2
public class KafkaConsumeConfig {


    @Autowired
    private KafkaProperties kafkaProperties;


    /**
     * kafka-consume 配置参数
     */
    public Map<String, Object> getConsumeProperties(KafkaProperties kafkaProperties){
        log.info("kafka-common start getConsumeProperties");
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrap().getServers());
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getConsumer().getGroupId());
        configs.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, kafkaProperties.getConsumer().getAutoCommitInterValMs());
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaProperties.getConsumer().getAutoOffsetReset());
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getConsumer().getKeySerializer());
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getConsumer().getValueSerializer());
        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaProperties.getConsumer().isEnableAutoCommit());
        configs.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, kafkaProperties.getConsumer().getMaxPollRecords());
        configs.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG,kafkaProperties.getConsumer().getMaxPollIntervalMs());
        configs.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, kafkaProperties.getConsumer().getSessionTimeoutMs());
        configs.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG,kafkaProperties.getConsumer().getHeartbeatIntervalMs());

        if (StringUtils.isNotBlank(kafkaProperties.getConsumer().getClientId())){
            configs.put(ConsumerConfig.CLIENT_ID_CONFIG,kafkaProperties.getConsumer().getClientId());
        }
        return configs;
    }


    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        log.info("kafka-common start getConsumeProperties");
        Map<String, Object> properties = getConsumeProperties(kafkaProperties);
        return new DefaultKafkaConsumerFactory<>(properties);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        log.info("kafka-common start kafkaListenerContainerFactory");
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
        factory.setConsumerFactory(consumerFactory());
        factory.setBatchListener(this.kafkaProperties.getConsumer().getBatchListener());
        factory.setConcurrency(this.kafkaProperties.getConsumer().getConcurrency());
        factory.getContainerProperties().setPollTimeout(this.kafkaProperties.getConsumer().getPoolTimeOut());
        return factory;
    }

}

package com.platform.kafka.config;

import com.platform.kafka.annotation.FoxKafkaProducer;
import com.platform.kafka.condition.ProduceCondition;
import com.platform.kafka.producer.impl.KafkaMessageProducer;
import com.platform.kafka.properties.KafkaProperties;
import com.platform.kafka.util.ReflectUtils;
import com.platform.spring.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.kafka.support.ProducerListener;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wulinhao
 * @ClassName: KafkaProducerConfig
 * @Description: TODO
 * @date 2020/7/9下午6:31
 *
 */


@Conditional(ProduceCondition.class)
@Configuration
@EnableConfigurationProperties(KafkaProperties.class)
@EnableKafka
public class KafkaProducerConfig implements BeanPostProcessor{

    @Autowired
    private KafkaProperties kafkaProperties;

    private String topic;

    private boolean flush;


    /**
     * kafka-produce 连接对象
     */
    @Bean
    @ConditionalOnMissingBean(KafkaTemplate.class)
    public KafkaTemplate<String, String> kafkaTemplate() {
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory());
        return kafkaTemplate;
    }


    /**
     * kafka连接配置参数
     * @param kafkaProperties
     * @return
     */
    public Map<String, Object> getProduceProperties(KafkaProperties kafkaProperties) {
        Map<String, Object> configs = new HashMap<String, Object>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrap().getServers());
        configs.put(ProducerConfig.RETRIES_CONFIG, kafkaProperties.getProducer().getRetries());
        configs.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProperties.getProducer().getBatchSize());
        configs.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProperties.getProducer().getLingerMs());
        configs.put(ProducerConfig.BUFFER_MEMORY_CONFIG, kafkaProperties.getProducer().getBufferMemory());
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProperties.getProducer().getKeySerializer());
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProperties.getProducer().getValueSerializer());
        configs.put(ProducerConfig.ACKS_CONFIG, kafkaProperties.getProducer().getAcks());

        if (StringUtils.isNotBlank(kafkaProperties.getProducer().getClientId())){
            configs.put(ConsumerConfig.CLIENT_ID_CONFIG,kafkaProperties.getProducer().getClientId());
        }

        //如果开启幂等，ACK默认为all
        if (null!=kafkaProperties.getProducer().getEnableIdempotence()&&kafkaProperties.getProducer().getEnableIdempotence()){
            configs.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,true);
            configs.put(ProducerConfig.ACKS_CONFIG,"all");
        }
        return configs;
    }


    @Bean
    @ConditionalOnMissingBean(ProducerListener.class)
    public ProducerListener<Object, Object> kafkaProducerListener() {
        return new LoggingProducerListener<>();
    }


    @Bean
    @ConditionalOnMissingBean(ProducerFactory.class)
    public ProducerFactory<String, String> producerFactory() {
        Map<String,Object> properties = getProduceProperties(kafkaProperties);
        DefaultKafkaProducerFactory<String, String> factory = new DefaultKafkaProducerFactory<>(properties);
        return factory;
    }


    /**
     * 类加载时调用
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            FoxKafkaProducer annotation = field.getAnnotation(FoxKafkaProducer.class);
            if(annotation != null){
                this.topic = annotation.topic();
                this.flush = annotation.flush();
                if (StringUtils.isBlank(this.topic)){
                      throw new ServiceException("Please set kafka topic");
                }
                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                KafkaMessageProducer kafkaMessageProducer = new KafkaMessageProducer();
                kafkaMessageProducer.setTopic(this.topic);
                kafkaMessageProducer.setFlush(this.flush);
                kafkaMessageProducer.setKafkaTemplate(kafkaTemplate());
                ReflectUtils.setFieldValue(bean,field.getName(),KafkaMessageProducer.class,kafkaMessageProducer);
                field.setAccessible(isAccessible);
            }
        }
        return bean;
    }


}

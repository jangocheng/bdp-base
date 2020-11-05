package com.platform.kafka.annotation;

import com.platform.kafka.config.KafkaProducerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author wlhbdp
 * @ClassName: FoxKafkaProducer
 * @Description: Kafka生产者注解
 *
 */


@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD })
@Documented
@Import({ KafkaProducerConfig.class })
@Configuration
public @interface FoxKafkaProducer {
    String topic() default "";
    boolean flush() default true;
}

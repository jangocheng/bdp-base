package com.platform.config;

import com.platform.config.property.HadoopProperties;
import com.platform.config.property.ModelParams;
import com.platform.config.property.TopicProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * author: wlhbdp
 * create: 2020-05-21 13:29
 */
@Component
@ConfigurationProperties(prefix = "app-config")
@Data
public class AppConfiguration {
    @NestedConfigurationProperty
    private TopicProperties topics;
    @NestedConfigurationProperty
    private ModelParams modelParams;
    @NestedConfigurationProperty
    private HadoopProperties hadoop;

    @Bean("topics")
    public TopicProperties topicProperties() {
        return this.topics;
    }

    @Bean
    public ModelParams modelParams() { return this.modelParams; }

    @Bean("hadoopProperties")
    public HadoopProperties hadoopProperties() {
        return this.hadoop;
    }
}

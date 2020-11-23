package com.platform.base.adapter.common.config;

import com.platform.base.adapter.common.config.property.HttpClientProperties;
import com.platform.base.adapter.common.config.property.ThirdParty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * author: wlhbdp
 * create: 2020-01-08 18:12
 */
@Component
@ConfigurationProperties(prefix = "app-config")
@Data
public class AppConfig {
    @NestedConfigurationProperty
    private ThirdParty thirdParty;

    @NestedConfigurationProperty
    private HttpClientProperties httpClientProperties;

    @Bean("thirdParty")
    public ThirdParty thirdParty() {
        return this.thirdParty;
    }

    @Bean("httpClientProperties")
    public HttpClientProperties httpClientProperties() {
        return this.httpClientProperties;
    }
}

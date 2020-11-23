package com.platform.base.adapter.common.config;

import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * author: wlhbdp
 * create: 2020-06-22 09:31
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class RestTemplateConfig {
    @Resource
    private CloseableHttpClient httpClient;

    @Bean({"restTemplate", "dingTalkRobotRestTemplate"})
    public RestTemplate restTemplate(@Qualifier("mappingJackson2HttpMessageConverter") MappingJackson2HttpMessageConverter
                                                 jackson2HttpMessageConverter) {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("utf-8"));
        messageConverters.add(stringHttpMessageConverter);
        messageConverters.add(jackson2HttpMessageConverter);
        restTemplate.setMessageConverters(messageConverters);

        return restTemplate;
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory rf = new HttpComponentsClientHttpRequestFactory();
        rf.setHttpClient(httpClient);
        return rf;
    }
}

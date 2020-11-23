package com.platform.finance.report.common.core;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * @author wlhbdp
 * @create 2020-01-17 下午3:44
 */
public class MybatisConfig {
    @Autowired
    private MybatisProperties properties;

    @Autowired(required = false)
    private Interceptor[] interceptors;

    @Autowired
    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Bean(name = "sqlSessionFactory")
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        if (StringUtils.hasText(this.properties.getConfig())) {
            factory.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfig()));
        } else {
            if (interceptors != null && this.interceptors.length > 0) {
                factory.setPlugins(this.interceptors);
            }
            factory.setConfigurationProperties(this.properties.getConfigurationProperties());
        }
        return factory.getObject();
    }

    @Bean
    public MybatisPageInterceptor pageInterceptor() {
        return new MybatisPageInterceptor();
    }
}

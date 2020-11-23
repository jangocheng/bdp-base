package com.platform.report.common.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.properties.PropertiesConfigurationBuilder;

import java.net.URI;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;

/**
 * Log4j2的Apollo配置类
 * <dl>
 * <dt>主要实现功能：</dt>
 * <dd>
 * <ol>
 * <li>log4j2初始化时通过该类从Apollo加载相应的log4j2的配置信息</li>
 * <li>当通过Apollo修改log4j2配置后，能立即生效。例如，动态修改日志级别。</li>
 * </ol>
 * </dd>
 * </dl>
 */
@Plugin(name = "ApolloLog4j2ConfigurationFactory", category = ConfigurationFactory.CATEGORY)
@Order(100)
public class ApolloLog4j2ConfigurationFactory extends ConfigurationFactory {

    /**
     * Log4j2的NameSpace名称
     */
    private static final String LOG4J2_NAMESPACE = "bigdata.java.log4j2";

    @Override
    protected String[] getSupportedTypes() {
        return new String[]{"*"};
    }

    @Override
    public Configuration getConfiguration(LoggerContext loggerContext, ConfigurationSource source) {
        return getConfiguration(loggerContext, source.toString(), null);
    }

    @Override
    public Configuration getConfiguration(LoggerContext loggerContext, String name, URI configLocation) {
        // 从Apollo读取log4j2配置
        Config config = ConfigService.getConfig(LOG4J2_NAMESPACE);
        Set<String> propertyNames = config.getPropertyNames();

        Properties properties = new Properties();
        for (String propertyName : propertyNames) {
            String propertyValue = config.getProperty(propertyName, null);
            properties.setProperty(propertyName, propertyValue);
        }

        // 添加log4j2配置的监听器
        config.addChangeListener(new Log4j2ConfigChangeListener(properties));

        // 构造log4j2的Configuration
        return new PropertiesConfigurationBuilder()
                .setRootProperties(copyProperties(properties))
                .setLoggerContext(loggerContext)
                .build();
    }

    /**
     * 复制Properties
     *
     * @param properties 原Properties对象
     * @return 新Properties对象
     */
    private Properties copyProperties(Properties properties) {
        Properties newProperties = new Properties();

        Enumeration<String> enumeration = (Enumeration<String>) properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String propertyName = enumeration.nextElement();
            newProperties.put(propertyName, properties.getProperty(propertyName));
        }
        return newProperties;
    }

    private class Log4j2ConfigChangeListener implements ConfigChangeListener {

        private Properties configProperties;

        public Log4j2ConfigChangeListener(Properties configProperties) {
            this.configProperties = configProperties;
        }

        public void onChange(ConfigChangeEvent changeEvent) {
            String newValue;
            ConfigChange configChange;

            for (String changedKey : changeEvent.changedKeys()) {
                configChange = changeEvent.getChange(changedKey);
                newValue = configChange.getNewValue();
                configProperties.put(changedKey, newValue);
            }

            // 构造新配置并应用到LoggerContext
            LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
            Configuration newConfiguration = new PropertiesConfigurationBuilder()
                    .setRootProperties(copyProperties(configProperties))
                    .setLoggerContext(ctx)
                    .build();
            ctx.updateLoggers(newConfiguration);
        }
    }
}

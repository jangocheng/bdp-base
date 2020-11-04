package com.platform.spring.util.spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * properties配置文件上下文
 */
public class PropertiesContext {

    // 配置对象
    private Properties properties = new Properties();

    private PropertiesContext() {
    }

    public static PropertiesContext getInstall() {
        return PropertiesContextHolder.INSTALL;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getPropertyValue(String key) {
        return getPropertyValue(key, StringUtils.EMPTY);
    }

    public String getPropertyValue(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static String getString(String key, String defaultValue) {
        return getInstall().getPropertyValue(key, defaultValue);
    }

    public static String getString(String key) {
        return getString(key, StringUtils.EMPTY);
    }

    public static int getInt(String key) {
        return getInt(key, 0);
    }

    public static int getInt(String key, int defaultValue) {
        String value = getString(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return Integer.valueOf(value);
    }

    public static Properties findProperties(String prefix) {
        Properties props = getInstall().getProperties();
        Properties findProps = new Properties();
        if (props == null || prefix == null) {
            return props;
        }
        Enumeration<?> en = props.propertyNames();
        String key;
        int len = prefix.length();
        while (en.hasMoreElements()) {
            key = (String) en.nextElement();
            if (key.startsWith(prefix)) {
                findProps.put(key.substring(len), props.getProperty(key));
            }
        }
        return findProps;
    }

    /**
     * 获得property list
     * 
     * @param prefix 前缀
     * @return
     */
    public static List<String> getList(String prefix) {
        Properties props = getInstall().getProperties();
        if (props == null || prefix == null) {
            return Collections.emptyList();
        }

        List<String> list = new ArrayList<String>();
        Enumeration<?> en = props.propertyNames();
        String key;
        while (en.hasMoreElements()) {
            key = (String) en.nextElement();
            if (key.startsWith(prefix)) {
                list.add(props.getProperty(key));
            }
        }
        return list;
    }

    public static Map<String, String> getMap(String prefix) {
        Properties props = getInstall().getProperties();
        if (props == null || prefix == null) {
            return Collections.emptyMap();
        }
        Map<String, String> map = new HashMap<String, String>();
        Enumeration<?> en = props.propertyNames();
        String key;
        int len = prefix.length();
        while (en.hasMoreElements()) {
            key = (String) en.nextElement();
            if (key.startsWith(prefix)) {
                map.put(key.substring(len), props.getProperty(key));
            }
        }
        return map;
    }

    private static class PropertiesContextHolder {
        // PropertyContext单例
        private final static PropertiesContext INSTALL = new PropertiesContext();
        static {
            // do something.....
        }
    }

}

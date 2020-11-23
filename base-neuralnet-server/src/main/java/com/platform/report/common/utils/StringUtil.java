package com.platform.report.common.utils;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
    /**
     * 如果字符串为空或null返回null,如果字符串前后有空格去除空格
     *
     * @param value
     * @return
     */
    public static String getValidValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return value.trim();
    }
}

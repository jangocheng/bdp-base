package com.platform.base.adapter.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;

public final class ModelParamUtils {

    /**
     * 缺失值处理
     *
     * @param params           需要处理的对象
     * @param missingFillValue 缺失值
     */
    public static void dealMissingValue(JSONObject params, Object missingFillValue) {
        if (null == params) {
            return;
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof String) {
                value = StringUtils.isNotBlank(value.toString()) ? value : missingFillValue.toString();
            } else {
                value = null == value ? missingFillValue : value;
            }

            entry.setValue(value);
        }

    }

    /**
     * 检测模型参数
     *
     * @param clazz
     * @param dataSet dataSet不能为null
     * @param <T>
     * @return
     */
    public static <T> Boolean checkModelParams(Class<T> clazz, JSONObject... dataSet) {
        Field[] fields = clazz.getDeclaredFields();

        HashSet<String> keys = new HashSet<>();
        for (JSONObject data : dataSet) {
            keys.addAll(data.keySet());
        }

        for (Field field : fields) {
            if (!keys.contains(field.getName())) {
                return false;
            }

        }
        return true;
    }


}

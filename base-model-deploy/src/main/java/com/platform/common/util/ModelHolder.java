package com.platform.common.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author: wlhbdp
 * create: 2020-05-31 13:33
 */
public class ModelHolder {

    private static Map<String, Object> modelHolder = new ConcurrentHashMap<>();

    public static int getModelSize() {
        return modelHolder.size();
    }

    public static Object getModel(String modelName) {
        return modelHolder.get(modelName);
    }

    public static void setModel(String modelName, Object model) {
        modelHolder.put(modelName, model);
    }

    public static void setModel(Map<String, Object> map) {
        modelHolder.putAll(map);
    }
}

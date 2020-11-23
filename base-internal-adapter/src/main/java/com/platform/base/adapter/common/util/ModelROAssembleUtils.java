package com.platform.base.adapter.common.util;

import com.platform.base.adapter.service.impl.ro.ModelRO;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组装模型预测的请求参数
 * author: wlhbdp
 * create: 2020-06-26 15:17
 */
public class ModelROAssembleUtils {

    public static ModelRO parseDataForModelRO(String modelType, String modelName,
                                        String outputName, String outputType,
                                        Object... values) throws IllegalAccessException {
        ModelRO modelRO = new ModelRO();
        //model
        modelRO.setModel(new ModelRO.NameValuePair(modelType, modelName));
        List<ModelRO.NameValuePair> fields = new ArrayList<>();
        List<List<Object>> rows = new ArrayList<>();
        List<Object> row = new ArrayList<>();

        Map<String, Map<String, Object>> map = new HashMap<>();
        for (Object obj: values) {
            map.putAll(getObjectInfo(obj));
        }
        for (Map.Entry<String, Map<String, Object>> entry: map.entrySet()) {
            String fieldName = entry.getKey();
            Map<String, Object> nameValuePair = entry.getValue();

            for (Map.Entry<String, Object> e: nameValuePair.entrySet()) {
                String fieldType = e.getKey();
                Object value = e.getValue();
                //System.out.println("k:" + fieldType + ", v:" + value);
                switch (fieldType) {
                    case "Integer":
                    case "Boolean":
                        fieldType = "int";break;
                    case "String": fieldType = "string";break;
                    case "Double": fieldType = "double";break;
                    case "Float": fieldType = "float";break;
                }
                fields.add(new ModelRO.NameValuePair(fieldName, fieldType));
                row.add(value);
            }

        }
        rows.add(row);

        //schema
        ModelRO.FeatureSchema schema = new ModelRO.FeatureSchema(fields);
        modelRO.setSchema(schema);
        //output
        modelRO.setOutput(new ModelRO.NameValuePair(outputName, outputType));
        //rows
        modelRO.setRows(rows);

        return modelRO;
    }

    /**
     * fieldName:{fieldType, value}
     * @param t object
     * @param <T> object Type
     * @return A map contains type and value information of t
     */
    private static <T> Map<String, Map<String, Object>> getObjectInfo(T t) throws IllegalAccessException {
        Field[] fields = t.getClass().getDeclaredFields();
        Map<String, Map<String, Object>> map = new HashMap<>();
        for (Field field: fields) {
            Map<String, Object> pair = new HashMap<>();
            String fieldName = field.getName();
            String fieldType = field.getType().getSimpleName();
            boolean isAccessible = field.isAccessible();
            field.setAccessible(true);
            Object fieldValue = field.get(t);
            if ("Boolean".equals(fieldType)) {
                fieldType = "Integer";
                fieldValue = (Boolean)fieldValue ? 1 : 0;
            }
            if ("Integer".equals(fieldType) || "Double".equals(fieldType)) {
                fieldValue = fieldValue==null ? 0 : fieldValue;
            }
            pair.put(fieldType, fieldValue);
            field.setAccessible(isAccessible);
            map.put(fieldName, pair);
        }
        return map;
    }
}

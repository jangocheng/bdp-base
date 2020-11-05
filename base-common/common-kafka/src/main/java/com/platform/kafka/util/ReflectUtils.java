package com.platform.kafka.util;

import com.platform.spring.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author wlhbdp
 * @ClassName: ReflectUtils
 *
 */


public class ReflectUtils {
    /**
     * 取得字段值
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(Object obj, String fieldName) throws ServiceException{
        if (obj == null || fieldName == null || "".equals(fieldName)) {
            return null;
        }

        Class<?> clazz = obj.getClass();
        try {
            String methodname = "get" + StringUtils.capitalize(fieldName);
            Method method = clazz.getDeclaredMethod(methodname);
            method.setAccessible(true);
            return method.invoke(obj);
        } catch (Exception e) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(obj);
            } catch (Exception e1) {
                throw new ServiceException("get field value error!",e1);
            }
        }
    }

    /**
     * 设置字段值
     * @param target
     * @param fname
     * @param fieldClass
     * @param fieldObj
     */
    public static void setFieldValue(Object target, String fname, Class<?> fieldClass,
                                     Object fieldObj) throws ServiceException{
        if (!fieldClass.isAssignableFrom(fieldObj.getClass())) {
            return;
        }
        Class<?> clazz = target.getClass();
        try {
            Method method = clazz.getDeclaredMethod("set" + Character.toUpperCase(fname.charAt(0))
                    + fname.substring(1), fieldClass);
            method.setAccessible(true);
            method.invoke(target, fieldObj);
        } catch (Exception e) {
            try {
                Field field = clazz.getDeclaredField(fname);
                field.setAccessible(true);
                field.set(target, fieldObj);
            } catch (Exception e1) {
                throw new ServiceException("set field value error!",e1);
            }
        }
    }
}

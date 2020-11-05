package com.platform.hbase.util;

import com.platform.spring.exception.ServiceException;
import org.apache.hadoop.hbase.client.Put;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author wlhbdp
 * @ClassName: HPutUtil
 * @Description: put工具类
 *
 */
public class HPutUtil {

    public static Put getPut(Object val,Put put,byte [] family) throws Exception{
        if (null==val || null==put || null==family){
            throw new ServiceException("params is null");
        }

        //获得实体类名
        Class clazz=val.getClass();
        //获得属性
        Field[] fields = val.getClass().getDeclaredFields();
        //获得Object对象中的所有方法
        for(Field field:fields){
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
            //获得get方法
            Method getMethod = pd.getReadMethod();
            //此处为执行该Object对象的get方法
            Object getObj = getMethod.invoke(val);
            Type type=getMethod.getGenericReturnType();
            put.addColumn(family, HBytesUtil.toBytes(field.getName()), HBytesUtil.toBytes(getObj,type.getTypeName()));
        }
        return put;
    }

}

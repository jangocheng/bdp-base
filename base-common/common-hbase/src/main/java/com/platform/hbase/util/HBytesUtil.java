package com.platform.hbase.util;


import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.util.Bytes;

import com.platform.spring.exception.ServiceException;

public class HBytesUtil{


	public static byte[] toBytes(Object val,String type) throws ServiceException{
		if (String.class.getName().equals(type)){
            return toBytes(val != null ? val.toString() : null);
        }

        if (Boolean.class.getName().equals(type)) {
            return toBytes(val !=null ? (Boolean)val : null);
        }
        if (Long.class.getName().equals(type)) {
            return toBytes(val !=null ? (Long)val : null);
        }
        if (Float.class.getName().equals(type)) {
            return toBytes(val !=null ? (Float) val : null);
        }

        if (Double.class.getName().equals(type)) {
            return toBytes(val != null ? (Double) val : null);
        }
        if (Integer.class.getName().equals(type)) {
            return toBytes(val != null ?(Integer) val : null);
        }
        if (Short.class.getName().equals(type)) {
            return toBytes(val != null ?(Short) val : null);
        }
        if (BigDecimal.class.getName().equals(type)) {
            return toBytes(val != null ? (BigDecimal)val : null);
        }

        return null;
	}

	
	public static byte[] toBytes(String val) throws ServiceException{
	    if (StringUtils.isBlank(val)){
	        val = "";
        }
		return Bytes.toBytes(val);
	}
	
	public static byte[] toBytes(Long val) throws ServiceException{
	    if (null==val){
	        val = 0l;
        }
		return Bytes.toBytes(val);
	}
	
	public static byte[] toBytes(Integer val) throws ServiceException{
	    if (null==val){
	        val = 0;
        }
		return Bytes.toBytes(val);
	}
	
	public static byte[] toBytes(Boolean val) throws ServiceException{
	    if (null==val){
	        val = false;
        }
		return Bytes.toBytes(val);
	}

    public static byte[] toBytes(Float val) throws ServiceException{
        if (null==val){
            val = 0f;
        }
        return Bytes.toBytes(val);
    }

    public static byte[] toBytes(Double val) throws ServiceException{
        if (null==val){
            val = 0d;
        }
        return Bytes.toBytes(val);
    }

    public static byte[] toBytes(Short val) throws ServiceException{
        if (null==val){
            val = 0;
        }
        return Bytes.toBytes(val);
    }

    public static byte[] toBytes(BigDecimal val) throws ServiceException{
        if (null==val){
            val = new BigDecimal(0);
        }
        return Bytes.toBytes(val);
    }


	
	public static byte[] toBytes(Object val,Object defaultVal) throws ServiceException{
		if (val == null) {
			val = defaultVal;
		}
		if (!val.getClass().getName().equals(defaultVal.getClass().getName())) {
			throw new ServiceException();
		}else{
			String defalutClassName = defaultVal.getClass().getName();
			if (defalutClassName.equals(String.class.getName())) {
				return toBytes(val.toString());
			}


			if (defalutClassName.equals(Boolean.class.getName())) {
				return toBytes((Boolean) val);
			}
			if (defalutClassName.equals(Long.class.getName())) {
				return toBytes((Long) val);
			}
			if (defalutClassName.equals(Float.class.getName())) {
				return toBytes((Float) val);
			}
			
			if (defalutClassName.equals(Double.class.getName())) {
				return toBytes((Double) val);
			}
			if (defalutClassName.equals(Integer.class.getName())) {
				return toBytes((Integer) val);
			}
			if (defalutClassName.equals(Short.class.getName())) {
				return toBytes((Short) val);
			}
			if (defalutClassName.equals(BigDecimal.class.getName())) {
				return toBytes((BigDecimal)val);
			}
			
		}
		return null;
	}
}

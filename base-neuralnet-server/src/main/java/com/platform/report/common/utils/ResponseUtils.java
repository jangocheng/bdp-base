package com.platform.report.common.utils;


import com.platform.report.common.constant.ResponseCode;
import com.platform.report.common.entity.MessageTO;
import org.apache.poi.ss.formula.functions.T;

/**
 * 返回给客户端的工具类
 * author: wlhbdp
 * create: 2018-10-17 11:13
 */
public final class ResponseUtils {

    private ResponseUtils() {

    }

    private static final String DEFAULT_SUCCESS = "SUCCESS";

    private static <T> MessageTO<T> response(Boolean status, Integer code, String message, T data) {
        MessageTO<T> result = new MessageTO<>();
        result.setSuccess(status);
        result.setCode(code);
        result.setMsg(message);
        result.setData(data);
        return result;
    }

    public static MessageTO success() {
        return response(Boolean.TRUE, ResponseCode.SUCCESS, DEFAULT_SUCCESS, null);
    }

    public static <T> MessageTO<T> success(T data) {
        return response(Boolean.TRUE, ResponseCode.SUCCESS, DEFAULT_SUCCESS, data);
    }

    public static <T> MessageTO<T> success(String msg, T data) {
        return response(Boolean.TRUE, ResponseCode.SUCCESS, msg, data);
    }

    public static MessageTO<T> success(Integer code, String msg, T data) {
        return response(Boolean.TRUE, code, msg, data);
    }

    public static MessageTO failure(Integer code, String msg) {
        return response(Boolean.FALSE, code, msg, null);
    }

    public static MessageTO<T> failure(Integer code, String msg, T data) {
        return response(Boolean.FALSE, code, msg, data);
    }

    public static MessageTO failure(String msg) {
        return response(Boolean.FALSE, ResponseCode.BAD_REQUEST, msg, null);
    }
}

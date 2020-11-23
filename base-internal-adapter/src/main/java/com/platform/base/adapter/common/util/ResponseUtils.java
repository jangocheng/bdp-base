package com.platform.base.adapter.common.util;

import com.platform.base.adapter.common.constant.ResponseCode;
import com.platform.base.adapter.entity.MessageTO;

/**
 * 返回给客户端的工具类
 * author: wlhbdp
 */
public class ResponseUtils {
    private static final String DEFAULT_SUCCESS = "SUCCESS";

    public static <T> MessageTO responseSuccess() {
        return response(ResponseCode.CODE_SUCCESS, null, DEFAULT_SUCCESS);
    }

    public static <T> MessageTO responseSuccess(T data) {
        return response(ResponseCode.CODE_SUCCESS, data, DEFAULT_SUCCESS);
    }

    public static <T> MessageTO responseSuccess(T data, String msg) {
        return response(ResponseCode.CODE_SUCCESS, data, msg);
    }

    public static <T> MessageTO responseFailure(T data, String msg) {
        return response(ResponseCode.CODE_FAILURE, data, msg);
    }

    public static MessageTO responseFailure(String msg) {
        return responseFailure(null, msg);
    }

    private static <T> MessageTO response(Integer code, T data, String message)  {
        return new MessageTO<>(code, message, data);
    }
}

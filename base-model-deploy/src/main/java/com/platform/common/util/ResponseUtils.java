package com.platform.common.util;

import com.platform.common.constant.ResponseConstants;
import com.platform.entity.MessageTO;

/**
 * author: wlhbdp
 * create: 2020-05-31 09:28
 */
public class ResponseUtils {

    public static MessageTO responseWithSuccess(Object data) {
        return response(ResponseConstants.CODE_SUCCESS, ResponseConstants.MSG_SUCCESS, data);
    }

    public static MessageTO responseWithSuccess(int code, String msg) {
        return response(code, msg, null);
    }

    public static MessageTO responseWithSuccess(String msg) {
        return response(ResponseConstants.CODE_SUCCESS,msg, null);
    }

    /**
     * 仅当发生异常，才会使用此类方法
     * @param code code
     * @param msg 消息
     * @return MessageTO
     */
    public static MessageTO responseWithFailure(int code, String msg) {
        return response(code, msg, null);
    }

    public static MessageTO responseWithFailure(String msg) {
        return response(ResponseConstants.CODE_FAILURE, msg, null);
    }

    public static MessageTO responseWithFailure() {
        return response(ResponseConstants.CODE_FAILURE, ResponseConstants.MSG_FAILURE, null);
    }

    private static MessageTO response(int code, String msg, Object data) {
        MessageTO messageTO = new MessageTO();
        messageTO.setCode(code);
        messageTO.setData(data);
        messageTO.setMsg(msg);
        return messageTO;
    }
}

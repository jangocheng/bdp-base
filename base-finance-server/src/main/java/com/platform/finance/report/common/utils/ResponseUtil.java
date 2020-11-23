package com.platform.finance.report.common.utils;


import com.platform.finance.report.common.constant.CommonConstants;
import com.platform.finance.report.common.mo.ResponseMO;

/**
 * @author wlhbdp
 * @date 2020/9/4 23:48
 * @description 响应处理方法
 */
public class ResponseUtil {

    public static ResponseMO response(int code, String msg, Object data, String debugInfo) {
        ResponseMO responseMO = new ResponseMO();
        responseMO.setCode(code);
        responseMO.setMsg(msg);
        responseMO.setData(data);
        responseMO.setDebugInfo(debugInfo);
        return responseMO;
    }

    public static ResponseMO response(int code, String msg, Object data) {
        return response(code, msg, data, null);
    }

    public static ResponseMO response(int code, String msg) {
        return response(code, msg, null, null);
    }

    public static ResponseMO success(String msg) {
        return response(CommonConstants.RESPONSE_CODE_SUCCESS, msg, null, null);
    }

    public static ResponseMO success() {
        return response(CommonConstants.RESPONSE_CODE_SUCCESS, null, null, null);
    }

    public static ResponseMO successWithData(Object data) {
        return response(CommonConstants.RESPONSE_CODE_SUCCESS, null, data, null);
    }

    public static ResponseMO error(int code, String message) {
        return response(code, message);
    }

    public static ResponseMO error(String message, String debugInfo) {
        return response(CommonConstants.RESPONSE_CODE_FAILURE, message, null, debugInfo);
    }

    public static ResponseMO error(String message) {
        return error(CommonConstants.RESPONSE_CODE_FAILURE, message);
    }


}

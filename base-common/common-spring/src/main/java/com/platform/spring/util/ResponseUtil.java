package com.platform.spring.util;

import com.platform.spring.bean.MessageTO;
import com.platform.spring.constant.ResultCode;

import java.util.Date;


public class ResponseUtil {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static MessageTO genSuccessResult() {
        return new MessageTO()
                .setSuccess(true)
                .setCode(ResultCode.SUCCESS)
                .setMsg(DEFAULT_SUCCESS_MESSAGE);
    }

    public static MessageTO genSuccessMessage(String message) {
        return new MessageTO()
                .setSuccess(true)
                .setCode(ResultCode.SUCCESS)
                .setMsg(message);
    }


    public static MessageTO genSuccessResult(Object obj) {
        return new MessageTO()
                .setSuccess(true)
                .setCode(ResultCode.SUCCESS)
                .setMsg(DEFAULT_SUCCESS_MESSAGE)
                .setObj(obj);
    }

    public static MessageTO genFailMessage(String message) {
        return new MessageTO()
                .setSuccess(false)
                .setCode(ResultCode.FAIL)
                .setMsg(message);
    }

    public static MessageTO genAnalysisNotCompleteResult(String message,String startDateStr) {
        Date endDate = new Date();
        Date startDate = DatetimeUtil.strToDate(startDateStr);
        boolean isLarge = DatetimeUtil.isLargeEqual(startDate,endDate);
        if (isLarge){
            startDate = endDate;
        }
        String timeMessage = DatetimeUtil.getDistanceSecondToString(startDate,endDate);
        return new MessageTO()
                .setSuccess(true)
                .setCode(ResultCode.ANALYSIS_NOT_COMPLETE)
                .setMsg(message+timeMessage);
    }


    public static MessageTO genResult(boolean isSuccess, int code, String msg) {
        return genResult(isSuccess, code, msg, null, null);
    }


    public static MessageTO genResult(boolean isSuccess, int code, String msg, Object obj, Object rows) {
        return new MessageTO().setSuccess(isSuccess)
                .setCode(code)
                .setMsg(msg)
                .setObj(obj)
                .setRows(rows);
    }

}

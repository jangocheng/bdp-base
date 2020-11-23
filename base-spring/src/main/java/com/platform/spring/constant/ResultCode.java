package com.platform.spring.constant;

/**
 * author: wlhbdp
 * create: 2018-07-10 15:47
 */
public interface ResultCode {

    Integer SUCCESS = 200;

    //业务相关返回码,从1000开始
    Integer FAIL = 1000;

    Integer ANALYSIS_NOT_COMPLETE =  1001;
}

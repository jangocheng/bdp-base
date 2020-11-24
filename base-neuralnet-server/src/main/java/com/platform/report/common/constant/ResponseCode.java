package com.platform.report.common.constant;

/**
 * author: wlhbdp
 * create: 2020-10-17 11:34
 */
public interface ResponseCode {
    /**
     * 参数非法
     */
    Integer ILLEGAL_ARGUMENT = 1001;

    Integer EMPTY_MESSAGE = 1002;

    Integer SUCCESS = 200;

    Integer NO_CONTENT = 204;

    Integer BAD_REQUEST = 400;

    Integer UNAUTHORIZED = 401;

    Integer FORBIDDEN = 403;

    Integer RESOURCE_NOT_FOUND = 404;

    Integer METHOD_NOT_ALLOWED = 405;

    Integer NOT_ACCEPTABLE = 406;

    Integer REQUEST_TIMEOUT = 408;

    Integer INTERNEL_SERVER_ERROR = 500;

    Integer API_NOT_IMPLEMENTED = 501;
}



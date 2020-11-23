package com.platform.base.adapter.common.constant;

public interface ResponseCode {
    /**
     * 请求成功
     */
    Integer CODE_SUCCESS = 0;
    /**
     * 请求失败
     */
    Integer CODE_FAILURE = 1;

    Integer REQUEST_SUCCESS = 0;

    Integer REQUEST_FAILURE = 1;

    /**
     * 参数非法
     */
    Integer ILLEGAL_ARGUMENT = 1001;

    Integer INTERNAL_EXCEPTION = 1002;

    Integer SUCCESS = 200;

    Integer NO_CONTENT = 204;

    Integer BAD_REQUEST = 400;

    Integer UNAUTHORIZED = 401;

    Integer FORBIDDEN = 403;

    Integer RESOURCE_NOT_FOUND = 404;

    Integer METHOD_NOT_ALLOWED = 405;

    Integer NOT_ACCEPTABLE = 406;

    Integer REQUEST_TIMEOUT = 408;

    Integer INTERNAL_SERVER_ERROR = 500;

    Integer API_NOT_IMPLEMENTED = 501;
}



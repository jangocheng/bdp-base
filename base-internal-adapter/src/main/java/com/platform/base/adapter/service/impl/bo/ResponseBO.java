package com.platform.base.adapter.service.impl.bo;

import lombok.Data;

/**
 * author: wlhbdp
 * create: 2020-06-22 17:04
 */
@Data
public class ResponseBO {

    private Integer code;

    private Object data;

    private String msg;

    private String debugInfo;
}

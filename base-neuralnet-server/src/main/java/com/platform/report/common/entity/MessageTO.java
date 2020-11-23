package com.platform.report.common.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * author: wlhbdp
 * create: 2018-10-17 11:08
 */
@Getter
@Setter
public class MessageTO<T> {

    private Boolean success;

    private Integer code;

    private String msg;

    private T data;
}

package com.platform.entity;


import lombok.Data;

/**
 * author: wlhbdp
 * create: 2020-05-31 09:22
 */
@Data
public class MessageTO {

    private int code;

    private String msg;

    private Object data;
}

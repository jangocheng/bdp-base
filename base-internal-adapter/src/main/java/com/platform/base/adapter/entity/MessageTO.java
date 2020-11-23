package com.platform.base.adapter.entity;

import lombok.Data;

@Data
public class MessageTO<T> {

    private Integer code;

    private String msg;

    private T data;

    public MessageTO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}

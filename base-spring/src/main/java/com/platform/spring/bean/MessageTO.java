package com.platform.spring.bean;


public class MessageTO {

    private boolean success = true;//是否成功
    private String msg;//信息
    private Object obj;//返回对象
    private Object rows;//集合
    private int code;// 状态代码

    public int getCode() {
        return code;
    }
    public MessageTO setCode(int code) {
        this.code = code;
        return this;
    }
    public Object getRows() {
        return rows;
    }
    public MessageTO setRows(Object rows) {
        this.rows = rows;
        return this;
    }
    public boolean isSuccess() {
        return success;
    }
    public MessageTO setSuccess(boolean success) {
        this.success = success;
        return this;
    }
    public Object getObj() {
        return obj;
    }
    public MessageTO setObj(Object obj) {
        this.obj = obj;
        return this;
    }
    public String getMsg() {
        return msg;
    }
    public MessageTO setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    @Override
    public String toString() {
        return "MessageTO{" +
                "success:" + success +
                ", msg:'" + msg + '\'' +
                ", obj:" + obj +
                ", rows:" + rows +
                ", code:" + code +
                '}';
    }
}

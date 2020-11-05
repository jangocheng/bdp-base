package com.platform.hbase.api;

/**
 * @author wlhbdp
 * @ClassName: HbaseSystemException
 * @Description: hbase异常处理
 *
 */
public class HbaseSystemException extends RuntimeException {

    public HbaseSystemException(Exception cause) {
        super(cause.getMessage(), cause);
    }

    public HbaseSystemException(Throwable throwable) {
        super(throwable.getMessage(), throwable);
    }
}

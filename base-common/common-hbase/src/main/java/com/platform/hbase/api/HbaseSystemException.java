package com.platform.hbase.api;

/**
 * @author wulinhao
 * @ClassName: HbaseSystemException
 * @Description: hbase异常处理
 * @date 2020/06/28下午3:43
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

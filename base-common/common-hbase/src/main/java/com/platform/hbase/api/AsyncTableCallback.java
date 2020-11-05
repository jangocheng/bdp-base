package com.platform.hbase.api;

import org.apache.hadoop.hbase.client.AsyncTable;

/**
 * @author wlhbdp
 * @ClassName: AsyncTableCallback
 * @Description:  Hbase异步连接回调类
 *
 */
public interface AsyncTableCallback<T> {

    /**
     * @param table
     * @return
     * @throws Throwable
     */
    T doInTable(AsyncTable table) throws Throwable;

}

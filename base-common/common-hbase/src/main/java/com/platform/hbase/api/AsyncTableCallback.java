package com.platform.hbase.api;

import org.apache.hadoop.hbase.client.AsyncTable;

/**
 * @author wulinhao
 * @ClassName: AsyncTableCallback
 * @Description:  Hbase异步连接回调类
 * @date 2020-08-2017:08
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

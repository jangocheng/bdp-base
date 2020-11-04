package com.platform.hbase.api;

import org.apache.hadoop.hbase.client.Result;


/**
 * @author wulinhao
 * @ClassName: RowMapper
 * @date 2020/06/28下午3:25
 *
 */
public interface RowMapper<T> {

    T mapRow(Result result, int rowNum) throws Exception;

}
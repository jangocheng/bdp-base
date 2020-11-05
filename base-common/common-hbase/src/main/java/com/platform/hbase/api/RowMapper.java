package com.platform.hbase.api;

import org.apache.hadoop.hbase.client.Result;


/**
 * @author wlhbdp
 * @ClassName: RowMapper
 *
 */
public interface RowMapper<T> {

    T mapRow(Result result, int rowNum) throws Exception;

}
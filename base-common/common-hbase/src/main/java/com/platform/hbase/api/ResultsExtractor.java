package com.platform.hbase.api;

import org.apache.hadoop.hbase.client.ResultScanner;

/**
 * @author wlhbdp
 * @ClassName: ResultsExtractor
 * @Description: copy from spring-data
 *
 */
public interface ResultsExtractor<T> {
    T extractData(ResultScanner var1) throws Exception;
}


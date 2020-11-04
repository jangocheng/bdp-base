package com.platform.hbase.api;

import org.apache.hadoop.hbase.client.ResultScanner;

/**
 * @author wulinhao
 * @ClassName: ResultsExtractor
 * @Description: copy from spring-data
 * @date 2020/10/8下午2:05
 *
 */
public interface ResultsExtractor<T> {
    T extractData(ResultScanner var1) throws Exception;
}


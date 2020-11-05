package com.platform.hbase.api;

/**   
* @Title: TableCallback.java
* @Description: Hbase回调类
*/

import org.apache.hadoop.hbase.client.Table;

/**
 * @author wlhbdp
 * @ClassName TableCallback
 * @Description Hbase回调类
 */
public interface TableCallback<T> {

	/**
	 * Hbase回调函数
	 * @param table
	 * @return
	 * @throws Throwable
	 */
	T doInTable(Table table) throws Throwable;


}

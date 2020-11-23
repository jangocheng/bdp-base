package com.platform.hive.comm.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wlhbdp
 * 通过分页把数据从impala导到mysql的工具类，表里面有rowid字段
 */

public class ImpalaDataToMysqlUtil {

	/**
	 * 获取多线程要执行的SQL，通过分页把数据从impala导到mysql，表里面有rowid字段
	 * 
	 * @param oneThreadSize
	 * @param pageSize
	 * @param tableName
	 * @param impalaSqlUtil
	 * @param requestPool
	 * @return
	 */
	public static List<List<String>> getFindDataSqlWithPageMutiThread(long oneThreadSize, int pageSize, String tableName, ImpalaSqlUtil impalaSqlUtil) {
		// pageSize不能为0
		if (pageSize == 0 || pageSize < 0) {
			return null;
		}
		// 单线程跑的数据量一定为pageSize的N倍
		if (oneThreadSize % pageSize != 0) {
			return null;
		}
		List<List<String>> sqlResultList = new ArrayList<List<String>>();
		// 数据总数
		long totalRecord = impalaSqlUtil.getTotalRecord(tableName);

		// 线程总数
		int totalThread = (int) Math.ceil((double) totalRecord / oneThreadSize);
		for (int i = 1; i <= totalThread; i++) {
			List<String> sqlList = new ArrayList<String>();
			long pageStart = oneThreadSize * (i - 1) / pageSize;
			long pageEnd = oneThreadSize * i / pageSize;
			for (long j = pageStart; j < pageEnd; j++) {
				long start = j * pageSize;
				long end = (j + 1) * pageSize;
				String sqlWithPage = "select * from " + tableName + " where rowid>" + start + " and rowid<=" + end;

				sqlList.add(sqlWithPage);
			}
			sqlResultList.add(sqlList);
		}
		return sqlResultList;
	}

	/**
	 * 获取要执行的SQL，通过分页把数据从impala导到mysql，表里面有rowid字段
	 * @param pageSize
	 * @param tableName
	 * @param impalaSqlUtil
	 * @param requestPool
	 * @return
	 */
	public static List<String> getFindDataSqlWithPage(int pageSize, String tableName, ImpalaSqlUtil impalaSqlUtil) {
		// pageSize不能为0
		if (pageSize == 0 || pageSize < 0) {
			return null;
		}
		// 数据总数
		long totalRecord = impalaSqlUtil.getTotalRecord(tableName);
		int pageTotal = (int) (totalRecord / pageSize + 1);
		List<String> sqlList = new ArrayList<String>();

		for (long page = 0; page < pageTotal; page++) {
			long start = page * pageSize;
			long end = (page + 1) * pageSize;
			String sqlWithPage = "select * from " + tableName + " where rowid>" + start + " and rowid<=" + end;
			sqlList.add(sqlWithPage);
		}
		return sqlList;
	}

}

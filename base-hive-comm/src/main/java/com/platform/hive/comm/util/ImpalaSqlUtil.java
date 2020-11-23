package com.platform.hive.comm.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 
 * @author wlhbdp
 * @Description impala工具类
 * 
 */

@Component("impalaSqlUtil")
public class ImpalaSqlUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	private final static Logger logger = LoggerFactory.getLogger(ImpalaSqlUtil.class);

	@Autowired
	@Qualifier("impalaDataSource")
	private DataSource dataSource;

	/**
	 * 执行多条sql
	 * 
	 * @param sqlArr
	 * @param requestPool
	 */
	public void execute(String[] sqlArr, String requestPool) {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			for (int i = 0; i < sqlArr.length; i++) {
				try {
					PreparedStatement psmSRP = conn.prepareStatement("SET REQUEST_POOL = " + requestPool);
					psmSRP.execute();
					psmSRP.close();
					PreparedStatement psmSD = conn.prepareStatement("SET DISABLE_UNSAFE_SPILLS = 0");
					psmSD.execute();
					psmSD.close();
					PreparedStatement psmSql = conn.prepareStatement(sqlArr[i]);
					psmSql.execute();
					psmSql.close();
				} catch (Exception e) {
					logger.error(sqlArr[i] + "执行异常：" + e.getMessage());
					// 遇到异常打印信息然后跳过(避免表不存在程序sql异常)
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e + "");
			}
		}
	}

	/**
	 * 执行单条sql
	 * 
	 * @param sql
	 * @param requestPool
	 */
	public void execute(String sql, String requestPool) {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement psmSRP = conn.prepareStatement("SET REQUEST_POOL = " + requestPool);
			psmSRP.execute();
			psmSRP.close();
			PreparedStatement psmSD = conn.prepareStatement("SET DISABLE_UNSAFE_SPILLS = 0");
			psmSD.execute();
			psmSD.close();
			PreparedStatement psmSql = conn.prepareStatement(sql);
			psmSql.execute();
			psmSql.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e + "");
			}
		}
	}

	/**
	 * 执行带参数的sql
	 * 
	 * @param sql
	 * @param arrParam
	 */
	public void execute(String sql, Object[] arrParam, String requestPool) {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement psmSRP = conn.prepareStatement("SET REQUEST_POOL = " + requestPool);
			psmSRP.execute();
			PreparedStatement psmSD = conn.prepareStatement("SET DISABLE_UNSAFE_SPILLS = 0");
			psmSD.execute();
			PreparedStatement psmSql = conn.prepareStatement(sql);
			for (int i = 0; i < arrParam.length; i++) {
				psmSql.setObject(i + 1, arrParam[i]);
			}
			psmSql.execute();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e + "");
			}
		}
	}

	/**
	 * 列表查询
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> findList(String sql, List<Object> params) {
		Connection conn = null;
		ResultSet rs = null;
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			conn = dataSource.getConnection();
			PreparedStatement psmSql = conn.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.size(); i++) {
					psmSql.setObject(i + 1, params.get(i));
				}
			}
			rs = psmSql.executeQuery();
			ResultSetMetaData m = rs.getMetaData();
			int columns = m.getColumnCount();

			Map<String, Object> map = null;
			// 查出来的内容组装成map
			while (rs.next()) {
				map = new HashMap<String, Object>();
				for (int i = 1; i <= columns; i++) {
					map.put(m.getColumnName(i), rs.getObject(i));
				}
				result.add(map);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e + "");
			return null;
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e + "");
			}
		}
	}

	/**
	 * 列表查询
	 *
	 * @param sql
	 * @param params
	 * @return
	 */
//	public List<Map<String, Object>> findList(String sql, List<Object> params, String requestPool) {
//		Connection conn = null;
//		ResultSet rs = null;
//		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
//		try {
//			conn = dataSource.getConnection();
//			PreparedStatement psmSRP = conn.prepareStatement("SET REQUEST_POOL = " + requestPool);
//			psmSRP.execute();
//			PreparedStatement psmSD = conn.prepareStatement("SET DISABLE_UNSAFE_SPILLS = 0");
//			psmSD.execute();
//			PreparedStatement psmSql = conn.prepareStatement(sql);
//			if (params != null) {
//				for (int i = 0; i < params.size(); i++) {
//					psmSql.setObject(i + 1, params.get(i));
//				}
//			}
//			rs = psmSql.executeQuery();
//			ResultSetMetaData m = rs.getMetaData();
//			int columns = m.getColumnCount();
//
//			Map<String, Object> map = null;
//			// 查出来的内容组装成map
//			while (rs.next()) {
//				map = new HashMap<String, Object>();
//				for (int i = 1; i <= columns; i++) {
//					map.put(m.getColumnName(i), rs.getObject(i));
//				}
//				result.add(map);
//			}
//			return result;
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e + "");
//			return null;
//		} finally {
//			try {
//				if (conn != null) {
//					conn.close();
//				}
//			} catch (SQLException e) {
//				logger.error(e + "");
//			}
//		}
//	}
	
	/**
	 * 列表查询
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Map<String, String>> findListStringValue(String sql, List<Object> params) {
		Connection conn = null;
		ResultSet rs = null;
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		try {
			conn = dataSource.getConnection();
			PreparedStatement psmSql = conn.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.size(); i++) {
					psmSql.setObject(i + 1, params.get(i));
				}
			}
			rs = psmSql.executeQuery();
			ResultSetMetaData m = rs.getMetaData();
			int columns = m.getColumnCount();

			Map<String, String> map = null;
			// 查出来的内容组装成map
			while (rs.next()) {
				map = new HashMap<String, String>();
				for (int i = 1; i <= columns; i++) {
					map.put(m.getColumnName(i), rs.getString(i));
				}
				result.add(map);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e + "");
			return null;
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e + "");
			}
		}
	}
	
	/**
	 * 列表查询
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
//	public List<Map<String, String>> findListStringValue(String sql, List<Object> params, String requestPool) {
//		Connection conn = null;
//		ResultSet rs = null;
//		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
//		try {
//			conn = dataSource.getConnection();
//			PreparedStatement psmSRP = conn.prepareStatement("SET REQUEST_POOL = " + requestPool);
//			psmSRP.execute();
//			PreparedStatement psmSD = conn.prepareStatement("SET DISABLE_UNSAFE_SPILLS = 0");
//			psmSD.execute();
//			PreparedStatement psmSql = conn.prepareStatement(sql);
//			if (params != null) {
//				for (int i = 0; i < params.size(); i++) {
//					psmSql.setObject(i + 1, params.get(i));
//				}
//			}
//			rs = psmSql.executeQuery();
//			ResultSetMetaData m = rs.getMetaData();
//			int columns = m.getColumnCount();
//
//			Map<String, String> map = null;
//			// 查出来的内容组装成map
//			while (rs.next()) {
//				map = new HashMap<String, String>();
//				for (int i = 1; i <= columns; i++) {
//					map.put(m.getColumnName(i), rs.getString(i));
//				}
//				result.add(map);
//			}
//			return result;
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e + "");
//			return null;
//		} finally {
//			try {
//				if (conn != null) {
//					conn.close();
//				}
//			} catch (SQLException e) {
//				logger.error(e + "");
//			}
//		}
//	}

	/**
	 * 查找一个库的所有表名
	 * 
	 * @param database
	 * @param requestPool
	 * @return
	 */
	public List<Map<String, Object>> findTableNames(String database, String requestPool) {
		Connection conn = null;
		ResultSet rs = null;
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			conn = dataSource.getConnection();
			PreparedStatement psmSRP = conn.prepareStatement("SET REQUEST_POOL = " + requestPool);
			psmSRP.execute();
			PreparedStatement psmSD = conn.prepareStatement("SET DISABLE_UNSAFE_SPILLS = 0");
			psmSD.execute();
			String sqlToUseDB = "use " + database;
			PreparedStatement psmSqlToUseDB = conn.prepareStatement(sqlToUseDB);
			psmSqlToUseDB.execute();
			PreparedStatement psmSqlToFindResult = conn.prepareStatement("show tables");
			rs = psmSqlToFindResult.executeQuery();
			ResultSetMetaData m = rs.getMetaData();
			int columns = m.getColumnCount();
			Map<String, Object> map = null;
			// 查出来的内容组装成map
			while (rs.next()) {
				map = new HashMap<String, Object>();
				for (int i = 1; i <= columns; i++) {
					map.put(m.getColumnName(i), rs.getObject(i));
				}
				result.add(map);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e + "");
			return null;
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e + "");
			}
		}
	}

	/**
	 * 单个查询
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
//	public Map<String, Object> findOne(String sql, List<Object> params, String requestPool) {
//		Connection conn = null;
//		ResultSet rs = null;
//		Map<String, Object> resultMap = null;
//		try {
//			conn = dataSource.getConnection();
//			PreparedStatement psmSRP = conn.prepareStatement("SET REQUEST_POOL = " + requestPool);
//			psmSRP.execute();
//			PreparedStatement psmSD = conn.prepareStatement("SET DISABLE_UNSAFE_SPILLS = 0");
//			psmSD.execute();
//			PreparedStatement psmSql = conn.prepareStatement(sql);
//			if (params != null) {
//				for (int i = 0; i < params.size(); i++) {
//					psmSql.setObject(i + 1, params.get(i));
//				}
//			}
//			rs = psmSql.executeQuery();
//			ResultSetMetaData m = rs.getMetaData();
//			int columns = m.getColumnCount();
//
//			// 查出来的内容组装成map,取第一个
//			if (rs.next()) {
//				resultMap = new HashMap<String, Object>();
//				for (int i = 1; i <= columns; i++) {
//					resultMap.put(m.getColumnName(i), rs.getObject(i));
//				}
//			}
//			return resultMap;
//		} catch (Exception e) {
//			logger.error(e + "");
//			return null;
//		} finally {
//			try {
//				if (conn != null) {
//					conn.close();
//				}
//			} catch (SQLException e) {
//				logger.error(e + "");
//			}
//		}
//	}
	
	/**
	 * 单个查询
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public Map<String, Object> findOne(String sql, List<Object> params) {
		Connection conn = null;
		ResultSet rs = null;
		Map<String, Object> resultMap = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement psmSql = conn.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.size(); i++) {
					psmSql.setObject(i + 1, params.get(i));
				}
			}
			rs = psmSql.executeQuery();
			ResultSetMetaData m = rs.getMetaData();
			int columns = m.getColumnCount();

			// 查出来的内容组装成map,取第一个
			if (rs.next()) {
				resultMap = new HashMap<String, Object>();
				for (int i = 1; i <= columns; i++) {
					resultMap.put(m.getColumnName(i), rs.getObject(i));
				}
			}
			return resultMap;
		} catch (Exception e) {
			logger.error(e + "");
			return null;
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e + "");
			}
		}
	}

	/**
	 * 获取数据总数
	 * 
	 * @param tableName
	 * @return
	 */
//	public long getTotalRecord(String tableName, String requestPool) {
//		String sqlToCount = "SELECT COUNT(*) AS total FROM " + tableName;
//		Connection conn = null;
//		ResultSet rs = null;
//		try {
//			conn = dataSource.getConnection();
//			PreparedStatement psmSRP = conn.prepareStatement("SET REQUEST_POOL = " + requestPool);
//			psmSRP.execute();
//			PreparedStatement psmSD = conn.prepareStatement("SET DISABLE_UNSAFE_SPILLS = 0");
//			psmSD.execute();
//			PreparedStatement psmSql = conn.prepareStatement(sqlToCount);
//			rs = psmSql.executeQuery();
//			// 查出来的内容组装成map,取第一个
//			if (rs.next()) {
//				return (long) rs.getObject(1);
//			}
//			return 0l;
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e + "");
//			return 0l;
//		} finally {
//			try {
//				if (conn != null) {
//					conn.close();
//				}
//			} catch (SQLException e) {
//				logger.error(e + "");
//			}
//		}
//	}
	
	/**
	 * 获取数据总数
	 * 
	 * @param tableName
	 * @return
	 */
	public long getTotalRecord(String tableName) {
		String sqlToCount = "SELECT COUNT(*) AS total FROM " + tableName;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement psmSql = conn.prepareStatement(sqlToCount);
			rs = psmSql.executeQuery();
			// 查出来的内容组装成map,取第一个
			if (rs.next()) {
				return (long) rs.getObject(1);
			}
			return 0l;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e + "");
			return 0l;
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e + "");
			}
		}
	}
}

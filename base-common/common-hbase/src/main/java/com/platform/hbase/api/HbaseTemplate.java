package com.platform.hbase.api;

/**   
* @Title: HbaseTemplate.java
* @Description: Hbase操作模板类
*/



import com.platform.spring.exception.ServiceException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName HbaseTemplate
 * @Description 
 */
public class  HbaseTemplate extends AbstractHbaseOperations {


	private static final Logger LOGGER = LogManager.getLogger(HbaseTemplate.class);

	private Configuration configuration;

	private volatile Connection connection;

	private HbaseConectionManager conectionManager = new HbaseConectionManager();

	public HbaseTemplate(Configuration configuration) {
		this.setConfiguration(configuration);
		Assert.notNull(configuration, " a valid configuration is required");
	}

	@Override
	public <T> T execute(String tableName, TableCallback<T> action) {
		Assert.notNull(action, "Callback object must not be null");
		Assert.notNull(tableName, "No table specified");
		Table table = null;
		try {
			table = this.getConnection().getTable(TableName.valueOf(tableName));
			return action.doInTable(table);
		} catch (Throwable throwable) {
			throw new HbaseSystemException(throwable);
		} finally {
			if (null != table) {
				try {
					table.close();
				} catch (IOException e) {
					LOGGER.error("hbase资源释放失败");
				}
			}
		}
	}

	@Override
	public <T> T get(String tableName, final String rowName, final String familyName, final String qualifier, final RowMapper<T> mapper) {
		return this.execute(tableName,table -> {
			Get get = getHbaseGet(familyName,qualifier,rowName);
			Result result = table.get(get);
			return mapper.mapRow(result, 0);
		});
	}




	@Override
	public <T> T find(String tableName, final Scan scan, final ResultsExtractor<T> action) {
		return this.execute(tableName, table -> {
			int caching = scan.getCaching();
			// 如果caching未设置(默认是1)，将默认配置成5000
			if (caching == 1) {
				scan.setCaching(5000);
			}
			ResultScanner scanner = table.getScanner(scan);
			try {
				return action.extractData(scanner);
			} finally {
				scanner.close();
			}
		});
	}



	/**
	 * 插入集合数据到Hbase
	 * @param tableName
	 * @param puts
	 * @return
	 */
	public boolean addPuts(String tableName,final List<Put> puts) throws ServiceException {
		boolean bool = execute(tableName, table -> {
			boolean flag = false;
			try {
				table.put(puts);
				flag = true;
			} catch (Exception e) {
				throw new ServiceException("插入Hbse异常!",e);
			}
			return flag;
		});
		return bool;
	}


	public Connection getConnection() {
		return conectionManager.getConnection(this.configuration);
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

}

package com.platform.hive.comm.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * 
 * @author wlhbdp
 * @Description impala数据源
 * 
 */

@Configuration
public class ImpalaDataSourceConfig {

	@ConfigurationProperties(prefix = "impala")
	@Bean("impalaDataSource")
	public DataSource getDataSource() {
		return new DriverManagerDataSource();
	}
}

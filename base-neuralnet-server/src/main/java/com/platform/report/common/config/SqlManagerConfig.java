package com.platform.report.common.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.log4j.Log4j2;
import org.beetl.sql.core.ClasspathLoader;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.UnderlinedNameConversion;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.ext.spring4.BeetlSqlDataSource;
import org.beetl.sql.ext.spring4.BeetlSqlScannerConfigurer;
import org.beetl.sql.ext.spring4.SqlManagerFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;


@Configuration
@Log4j2
public class SqlManagerConfig {


    @Resource

    @Bean(name = "dataSource")
    public DataSource dataSource(Environment env){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setPassword(env.getProperty("spring.datasource.druid.password"));
        druidDataSource.setUsername(env.getProperty("spring.datasource.druid.username"));
        druidDataSource.setDriverClassName(env.getProperty("spring.datasource.druid.driver-class-name"));
        druidDataSource.setUrl(env.getProperty("spring.datasource.druid.url"));
        druidDataSource.setInitialSize(Integer.valueOf(env.getProperty("spring.datasource.druid.initial-size")));
        druidDataSource.setMinIdle(Integer.valueOf(env.getProperty("spring.datasource.druid.min-idle")));
        druidDataSource.setMaxActive(Integer.valueOf(env.getProperty("spring.datasource.druid.max-active")));
        druidDataSource.setMaxWait(Long.valueOf(env.getProperty("spring.datasource.druid.max-wait")));
        druidDataSource.setTimeBetweenEvictionRunsMillis(Long.valueOf(env.getProperty("spring.datasource.druid.time-between-eviction-runs-millis")));
        druidDataSource.setMinEvictableIdleTimeMillis(Long.valueOf(env.getProperty("spring.datasource.druid.min-evictable-idle-time-millis")));
        druidDataSource.setValidationQuery(env.getProperty("spring.datasource.druid.validation-query"));
        druidDataSource.setTestWhileIdle(Boolean.valueOf(env.getProperty("spring.datasource.druid.test-while-idle")));
        druidDataSource.setTestOnBorrow(Boolean.valueOf(env.getProperty("spring.datasource.druid.test-on-borrow")));
        druidDataSource.setTestOnReturn(Boolean.valueOf(env.getProperty("spring.datasource.druid.test-on-return")));
        druidDataSource.setPoolPreparedStatements(Boolean.valueOf(env.getProperty("spring.datasource.druid.pool-prepared-statements")));
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.valueOf(env.getProperty("spring.datasource.druid.max-pool-prepared-statement-per-connection-size")));
        druidDataSource.setUseGlobalDataSourceStat(Boolean.valueOf(env.getProperty("spring.datasource.druid.use-global-data-source-stat")));
        druidDataSource.setConnectionProperties(env.getProperty("spring.datasource.druid.connection-properties"));

        try{
            druidDataSource.setFilters(env.getProperty("spring.datasource.druid.filters"));
        }catch (SQLException e) {
            log.error("set druid error!",e);
        }
        return druidDataSource;
    }

    /**
     * 注册Servlet信息， 配置监控视图
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ServletRegistrationBean druidServlet(Environment env) {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //登录查看信息的账号密码, 用于登录Druid监控后台
        servletRegistrationBean.addInitParameter("loginUsername", env.getProperty("spring.datasource.druid.stat-view-servlet.login-username"));
        servletRegistrationBean.addInitParameter("loginPassword", env.getProperty("spring.datasource.druid.stat-view-servlet.login-password"));
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable", env.getProperty("spring.datasource.druid.stat-view-servlet.reset-enable"));
        return servletRegistrationBean;
    }

    /**
     * 注册Filter信息, 监控拦截器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean filterRegistrationBean(Environment env) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", env.getProperty("spring.datasource.druid.web-stat-filter.exclusions"));
        filterRegistrationBean.addInitParameter("profile-enable", env.getProperty("spring.datasource.druid.web-stat-filter.profile-enable"));
        filterRegistrationBean.addInitParameter("principal-cookie-name", env.getProperty("spring.datasource.druid.web-stat-filter.principal-cookie-name"));
        filterRegistrationBean.addInitParameter("principal-session-name", env.getProperty("spring.datasource.druid.web-stat-filter.principal-session-name"));
        return filterRegistrationBean;
    }


    @Bean(name = "sqlManager")
    @Primary
    public SqlManagerFactoryBean getSqlManagerFactoryBean(@Qualifier("dataSource") DataSource master) {
        SqlManagerFactoryBean factoryBean = new SqlManagerFactoryBean();
        BeetlSqlDataSource source = new BeetlSqlDataSource();
        source.setMasterSource(master);
        factoryBean.setCs(source);
        factoryBean.setDbStyle(new MySqlStyle());
        //控制台或者日志系统输出执行的sql语句
        factoryBean.setInterceptors(new Interceptor[]{new DebugInterceptor()});
        //开启驼峰
        factoryBean.setNc(new UnderlinedNameConversion());
        //sql文件路径
        factoryBean.setSqlLoader(new ClasspathLoader("/sql"));
        return factoryBean;
    }


    @Bean(name = "sqlScannerConfigurer")
    public BeetlSqlScannerConfigurer getBeetlSqlScannerConfigurer() {
        BeetlSqlScannerConfigurer conf = new BeetlSqlScannerConfigurer();
        //mapper路径
        conf.setBasePackage("com.platform.report.dao");
        //后缀
        conf.setDaoSuffix("Dao");
        conf.setSqlManagerFactoryBeanName("sqlManager");
        return conf;
    }


    @Bean(name = "txManager")
    public DataSourceTransactionManager getDataSourceTransactionManager(@Qualifier("dataSource") DataSource datasource) {
        DataSourceTransactionManager dsm = new DataSourceTransactionManager();
        dsm.setDataSource(datasource);
        return dsm;
    }

}

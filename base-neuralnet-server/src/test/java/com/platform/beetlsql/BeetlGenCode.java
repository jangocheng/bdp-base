package com.platform.beetlsql;

import lombok.extern.log4j.Log4j2;
import org.beetl.sql.core.*;
import org.beetl.sql.core.db.DBStyle;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.ext.gen.GenConfig;
import org.beetl.sql.ext.gen.MapperCodeGen;


@Log4j2
public class BeetlGenCode {

    // JDBC配置，请修改为你项目的实际配置

    private static final String JDBC_URL = "jdbc:mysql://master/base-bi?useUnicode=true&characterEncoding=utf-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowMultiQueries=true";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "123456";
    private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.jdbc.Driver";


    public static void main(String[] args) {
        ConnectionSource source = ConnectionSourceHelper.getSimple(JDBC_DIVER_CLASS_NAME, JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        DBStyle mysql = new MySqlStyle();
        // sql语句放在classpagth的/sql 目录下
        SQLLoader loader = new ClasspathLoader("/sql");
        // 数据库命名跟java命名一样，所以采用DefaultNameConversion，还有一个是UnderlinedNameConversion，下划线风格的，
        UnderlinedNameConversion nc = new UnderlinedNameConversion();
        // 最后，创建一个SQLManager,DebugInterceptor 不是必须的，但可以通过它查看sql执行情况
        SQLManager sqlManager = new SQLManager(mysql, loader, source, nc, new Interceptor[]{new DebugInterceptor()});

        String tableName = "business_popu_province";
        try {

            GenConfig config = new GenConfig();
            config.preferBigDecimal(false);
            sqlManager.genPojoCode(tableName, "com.platform.report.bean", config);

            sqlManager.genSQLFile(tableName, config);

            MapperCodeGen mapper = new MapperCodeGen("com.platform.report.dao");
            config.codeGens.add(mapper);

            sqlManager.genPojoCode(tableName, "com.platform.report.bean", config);


        } catch (Exception e) {
            log.error(e);
        }



    }

}

package com.platform.finance.report.common.core;

import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * mybatis 分页插件，
 * 分页使用和 jpa 使用差不多
 * <p>
 * 分页时，mybaits mapper 接口中带 Pageable 参数 {@link Pageable
 * <p>
 * 返回参数是 Page {@link Page
 *
 * @author wlhbdp
 * @create 2020-01-17 下午3:17
 */
@Log4j2
@Intercepts({@Signature(method = "prepare", type = StatementHandler.class, args = {Connection.class, Integer.class}),
        @Signature(method = "query", type = Executor.class, args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class MybatisPageInterceptor implements Interceptor {

    private ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();
    private ThreadLocal<Pageable> pageableThreadLocal = new ThreadLocal<>();
    private ThreadLocal<Long> totalElementsThreadLocal = new ThreadLocal<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getTarget() instanceof StatementHandler) {
            //控制SQL和查询总数
            Pageable pageable = pageableThreadLocal.get();
            Page page = pageThreadLocal.get();
            if (pageable == null || page == null) {
                return invocation.proceed();
            }
            RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
            StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(handler, "delegate");
            BoundSql boundSql = delegate.getBoundSql();

            Connection connection = (Connection) invocation.getArgs()[0];

            Object parameterObj = boundSql.getParameterObject();
            MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");
            if (parameterObj instanceof MapperMethod.ParamMap) {
                MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) parameterObj;
                List<Object> pageableKey = new ArrayList<>();
                paramMap.forEach((k, v) -> {
                    if (v instanceof Pageable) {
                        pageableKey.add(k);
                    }
                });
                if (!CollectionUtils.isEmpty(pageableKey)) {
                    for (Object key : pageableKey) {
                        paramMap.remove(key);//移除Page参数
                    }
                }
            }
            totalElementsThreadLocal.remove();
            //获取总数
            long totalElements = queryTotalRecord(parameterObj, mappedStatement, connection);
            totalElementsThreadLocal.set(totalElements);


            String sql = boundSql.getSql();
            String pageSql = buildPageSql(pageable, sql);

            log.info("分页时, 生成分页pageSql: " + pageSql);
            ReflectUtil.setFieldValue(boundSql, "sql", pageSql);
            return invocation.proceed();
        } else {
            //查找分页对象
            Pageable pageable = findPageable(invocation.getArgs()[1]);
            if (pageable == null) {
                log.info("没有Page对象作为参数, 不是分页查询.");
                return invocation.proceed();
            }
            log.info("检测到分页Page对象, 使用分页查询.");
            Page page = new Page();
            page.setPageNumber(pageable.getPageNumber());
            page.setPageSize(pageable.getPageSize());
            pageThreadLocal.set(page);

            pageableThreadLocal.set(pageable);
            //设置真正的parameterObj
            invocation.getArgs()[1] = extractRealParameterObject(invocation.getArgs()[1]);
            try {
                Object resultObj = invocation.proceed(); // Executor.query(..)
                if (resultObj instanceof List) {
                    page.setContent((List) resultObj);
                    page.setTotalElements(totalElementsThreadLocal.get());
                    return page;
                }
                return resultObj;
            } finally {
                pageThreadLocal.remove();
                pageableThreadLocal.remove();
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 获取分页对象
     *
     * @param parameterObj
     * @return
     */
    private Pageable findPageable(Object parameterObj) {
        if (parameterObj instanceof Pageable) {
            return (Pageable) parameterObj;
        } else if (parameterObj instanceof Map) {
            for (Object val : ((Map<?, ?>) parameterObj).values()) {
                if (val instanceof Pageable) {
                    return (Pageable) val;
                }
            }
        }
        return null;
    }

    /**
     * 查询总数
     *
     * @param parameterObject
     * @param mappedStatement
     * @param connection
     * @throws SQLException
     * @author wlhbdp
     */
    private long queryTotalRecord(Object parameterObject, MappedStatement mappedStatement, Connection connection) throws SQLException {
        long totalRecord = 0L;
        BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
        String sql = boundSql.getSql();
        String countSql = this.buildCountSql(sql);
        log.debug("分页时, 生成countSql: " + countSql);

        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, parameterObject);
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, countBoundSql);
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(countSql);
            parameterHandler.setParameters(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                totalRecord = rs.getLong(1);
            }
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    log.warn("关闭ResultSet时异常.", e);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                    log.warn("关闭PreparedStatement时异常.", e);
                }
            }
        }
        return totalRecord;
    }

    /**
     * 生成Mysql分页查询SQL
     *
     * @param page
     * @param sql
     * @return
     * @author wlhbdp
     */
    protected String buildPageSql(Pageable page, String sql) {
        int offset = page.getPageNumber() * page.getPageSize();
        return new StringBuilder(sql).append(" limit ").append(offset).append(",").append(page.getPageSize()).toString();
    }

    /**
     * 根据原Sql语句获取对应的查询总记录数的Sql语句
     *
     * @param sql
     * @return
     */
    private String buildCountSql(String sql) {
        String tempSql = sql.toLowerCase();
        int index = tempSql.indexOf("from");
        String sub = sql.substring(index);
        if (sql.toLowerCase().contains("group by")) {
            StringBuilder builder = new StringBuilder();
            builder.append("from ( ");
            builder.append("SELECT 1 ");
            builder.append(sub);
            builder.append(" ) tb");
            sub = builder.toString();
        }
        return "select count(*) " + sub;
    }

    /**
     * <pre>
     * 把真正的参数对象解析出来
     * Spring会自动封装对个参数对象为Map<String, Object>对象
     * 对于通过@Param指定key值参数我们不做处理，因为XML文件需要该KEY值
     * 而对于没有@Param指定时，Spring会使用0,1作为主键
     * 对于没有@Param指定名称的参数,一般XML文件会直接对真正的参数对象解析，
     * 此时解析出真正的参数作为根对象
     * </pre>
     *
     * @param parameterObj
     * @return
     */
    protected Object extractRealParameterObject(Object parameterObj) {
        if (parameterObj instanceof Map<?, ?>) {
            Map<?, ?> parameterMap = (Map<?, ?>) parameterObj;
            if (parameterMap.size() == 2) {
                boolean springMapWithNoParamName = true;
                for (Object key : parameterMap.keySet()) {
                    if (!(key instanceof String)) {
                        springMapWithNoParamName = false;
                        break;
                    }
                    String keyStr = (String) key;
                    if (!"0".equals(keyStr) && !"1".equals(keyStr)) {
                        springMapWithNoParamName = false;
                        break;
                    }
                }
                if (springMapWithNoParamName) {
                    for (Object value : parameterMap.values()) {
                        if (!(value instanceof Page<?>)) {
                            return value;
                        }
                    }
                }
            }
        }
        return parameterObj;
    }

    /**
     * 利用反射进行操作的一个工具类
     */
    private static class ReflectUtil {

        /**
         * 利用反射获取指定对象的指定属性
         *
         * @param obj       目标对象
         * @param fieldName 目标属性
         * @return 目标属性的值
         */
        public static Object getFieldValue(Object obj, String fieldName) {
            Object result = null;
            Field field = ReflectUtil.getField(obj, fieldName);
            if (field != null) {
                field.setAccessible(true);
                try {
                    result = field.get(obj);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                    log.warn("", e);
                }
            }
            return result;
        }

        /**
         * 利用反射获取指定对象里面的指定属性
         *
         * @param obj       目标对象
         * @param fieldName 目标属性
         * @return 目标字段
         */
        private static Field getField(Object obj, String fieldName) {
            Field field = null;
            for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
                try {
                    field = clazz.getDeclaredField(fieldName);
                    break;
                } catch (NoSuchFieldException e) {
                    // 这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
                }
            }
            return field;
        }

        /**
         * 利用反射设置指定对象的指定属性为指定的值
         *
         * @param obj        目标对象
         * @param fieldName  目标属性
         * @param fieldValue 目标值
         */
        public static void setFieldValue(Object obj, String fieldName, String fieldValue) {
            Field field = ReflectUtil.getField(obj, fieldName);
            if (field != null) {
                try {
                    field.setAccessible(true);
                    field.set(obj, fieldValue);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    log.warn("", e);
                    e.printStackTrace();
                }
            }
        }
    }


}

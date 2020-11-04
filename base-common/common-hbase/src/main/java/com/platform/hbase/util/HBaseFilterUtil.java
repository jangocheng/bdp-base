package com.platform.hbase.util;

import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.filter.*;

import java.util.Collection;

/**
 * @author wulinhao
 * @ClassName: HBaseFilterUtil
 * @Description: hbase过滤工具
 * @date 2020/10/316:19 PM
 *
 */
public class HBaseFilterUtil {


    /**
     * 获得相等过滤器。相当于SQL的 [字段] = [值]
     * @param cf 列族名
     * @param col 列名
     * @param val 值
     * @return 过滤器
     */
    public static Filter eqFilter(byte[] cf, byte[] col, byte[] val) {
        SingleColumnValueFilter f = new SingleColumnValueFilter(cf, col, CompareOperator.EQUAL, val);
        f.setLatestVersionOnly(true);
        f.setFilterIfMissing(true);
        return f;
    }

    /**
     * 获得大于过滤器。相当于SQL的 [字段] > [值]
     * @param cf 列族名
     * @param col 列名
     * @param val 值
     * @return 过滤器
     */
    public static Filter gtFilter(byte[] cf, byte[] col, byte[] val) {
        SingleColumnValueFilter f = new SingleColumnValueFilter(cf, col, CompareOperator.GREATER, val);
        f.setLatestVersionOnly(true);
        f.setFilterIfMissing(true);
        return f;
    }

    /**
     * 获得大于等于过滤器。相当于SQL的 [字段] >= [值]
     * @param cf 列族名
     * @param col 列名
     * @param val 值
     * @return 过滤器
     */
    public static Filter gteqFilter(byte[] cf, byte[] col, byte[] val) {
        SingleColumnValueFilter f = new SingleColumnValueFilter(cf, col, CompareOperator.GREATER_OR_EQUAL, val);
        f.setLatestVersionOnly(true);
        f.setFilterIfMissing(true);
        return f;
    }

    /**
     * 获得小于过滤器。相当于SQL的 [字段] < [值]
     * @param cf 列族名
     * @param col 列名
     * @param val 值
     * @return 过滤器
     */
    public static Filter ltFilter(byte[] cf, byte[] col, byte[] val) {
        SingleColumnValueFilter f = new SingleColumnValueFilter(cf, col, CompareOperator.LESS, val);
        f.setLatestVersionOnly(true);
        f.setFilterIfMissing(true);
        return f;
    }

    /**
     * 获得小于等于过滤器。相当于SQL的 [字段] <= [值]
     * @param cf 列族名
     * @param col 列名
     * @param val 值
     * @return 过滤器
     */
    public static Filter lteqFilter(byte[] cf, byte[] col, byte[] val) {
        SingleColumnValueFilter f = new SingleColumnValueFilter(cf, col, CompareOperator.LESS_OR_EQUAL, val);
        f.setLatestVersionOnly(true);
        f.setFilterIfMissing(true);
        return f;
    }

    /**
     * 获得不等于过滤器。相当于SQL的 [字段] != [值]
     * @param cf 列族名
     * @param col 列名
     * @param val 值
     * @return 过滤器
     */
    public static Filter neqFilter(byte[] cf, byte[] col, byte[] val) {
        SingleColumnValueFilter f = new SingleColumnValueFilter(cf, col, CompareOperator.NOT_EQUAL, val);
        f.setLatestVersionOnly(true);
        f.setFilterIfMissing(true);
        return f;
    }

    /**
     * 和过滤器 相当于SQL的 的 and
     * @param filters 多个过滤器
     * @return 过滤器
     */
    public static Filter andFilter(Filter... filters) {
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        if(filters!=null && filters.length > 0) {
            if(filters.length > 1) {
                for (Filter f : filters) {
                    filterList.addFilter(f);
                }
            }
            if(filters.length == 1) {
                return filters[0];
            }
        }
        return filterList;
    }

    /**
     * 和过滤器 相当于SQL的 的 and
     * @param filters 多个过滤器
     * @return 过滤器
     */
    public static Filter andFilter(Collection<Filter> filters) {
        return andFilter(filters.toArray(new Filter[0]));
    }



    /**
     * 或过滤器 相当于SQL的 or
     * @param filters 多个过滤器
     * @return 过滤器
     */
    public static Filter orFilter(Filter... filters) {
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
        if(filters!=null && filters.length > 0) {
            for(Filter f : filters) {
                filterList.addFilter(f);
            }
        }
        return filterList;
    }

    /**
     * 或过滤器 相当于SQL的 or
     * @param filters 多个过滤器
     * @return 过滤器
     */
    public static Filter orFilter(Collection<Filter> filters) {
        return orFilter(filters.toArray(new Filter[0]));
    }

    /**
     * 非空过滤器 相当于SQL的 is not null
     * @param cf 列族
     * @param col 列
     * @return 过滤器
     */
    public static Filter notNullFilter(byte[] cf, byte[] col) {
        SingleColumnValueFilter filter = new SingleColumnValueFilter(cf,col, CompareOperator.NOT_EQUAL,new NullComparator());
        filter.setFilterIfMissing(true);
        filter.setLatestVersionOnly(true);
        return filter;
    }

    /**
     * 空过滤器 相当于SQL的 is null
     * @param cf 列族
     * @param col 列
     * @return 过滤器
     */
    public static Filter nullFilter(byte[] cf, byte[] col) {
        SingleColumnValueFilter filter = new SingleColumnValueFilter(cf, col, CompareOperator.EQUAL,new NullComparator());
        filter.setFilterIfMissing(false);
        filter.setLatestVersionOnly(true);
        return filter;
    }

    /**
     * 子字符串过滤器 相当于SQL的 like '%[val]%'
     * @param cf 列族
     * @param col 列
     * @param sub 子字符串
     * @return 过滤器
     */
    public static Filter subStringFilter(byte[] cf, byte[] col, String sub) {
        SingleColumnValueFilter filter = new SingleColumnValueFilter(cf, col, CompareOperator.EQUAL, new SubstringComparator(sub));
        filter.setFilterIfMissing(true);
        filter.setLatestVersionOnly(true);
        return filter;
    }

    /**
     * 正则过滤器 相当于SQL的 rlike '[regex]'
     * @param cf 列族
     * @param col 列
     * @param regex 正则表达式
     * @return 过滤器
     */
    public static Filter regexFilter(byte[] cf, byte[] col , String regex) {
        SingleColumnValueFilter filter = new SingleColumnValueFilter(cf, col,CompareOperator.EQUAL, new RegexStringComparator(regex));
        filter.setFilterIfMissing(true);
        filter.setLatestVersionOnly(true);
        return filter;
    }

}

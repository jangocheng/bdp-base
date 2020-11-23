package com.platform.finance.report.service.redis;

import java.util.concurrent.TimeUnit;

/**
 * @author wlhbdp
 * @create 2020-05-22 上午11:25
 */
public interface RedisService {

    /**
     * 让key指定有效期内过期
     *
     * @param key
     * @param expireTime
     * @return
     */
    Boolean expire(final String key, final Long expireTime);

    /**
     * 设值
     *
     * @param key
     * @param obj
     * @return
     */
    boolean set(String key, String obj);

    /**
     * 设值
     *
     * @param key
     * @param obj
     * @return
     */
    boolean set(String key, Object obj);

    /**
     * 获取值
     *
     * @param key
     * @return
     */
    String get(final String key);

    /**
     * 获取值
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T get(final String key, final Class<T> clazz);

    /**
     * @param key
     * @return
     */
    Boolean exists(final String key);

    /**
     * 删除指定值
     *
     * @param key
     * @return
     */
    Long delete(final String key);

    /**
     * 设值，如果key已经存在，则返回false
     *
     * @param key
     * @param value
     * @return
     */
    Boolean setNX(String key, String value);

    /**
     * 设值，指定某个时间内过期
     *  @param key
     * @param value
     * @param seconds
     */
    Void setEx(String key, String value, long seconds);

    /**
     * 原子自增
     * @param key
     * @return
     */
    Long incr(String key);

    /**
     * 原子加
     * @param key
     * @param value
     * @return
     */
    Double incrBy(String key, double value);

    /**
     * 获取锁
     * @param key
     * @param value
     * @param timeout
     * @param unit
     * @return
     */
    Boolean tryLock(String key, String value, long timeout, TimeUnit unit);
}

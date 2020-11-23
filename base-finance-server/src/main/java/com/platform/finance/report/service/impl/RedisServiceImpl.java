package com.platform.finance.report.service.impl;

import com.alibaba.fastjson.JSON;
import com.platform.finance.report.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author wlhbdp
 * @create 2020-05-22 上午11:27
 */
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static RedisSerializer<String> serializer = new StringRedisSerializer();

    @Override
    public Boolean expire(String key, Long expireTime) {
        return redisTemplate.execute((RedisCallback<Boolean>) conn ->
                conn.expire(serializer.serialize(key), expireTime));
    }

    @Override
    public boolean set(String key, String obj) {
        return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
            redisConnection.set(serializer.serialize(key), serializer.serialize(obj));
            return true;
        });
    }

    @Override
    public boolean set(String key, Object value) {
        String str = JSON.toJSONString(value);
        return set(key, str);
    }

    @Override
    public String get(String key) {
        return redisTemplate.execute((RedisCallback<String>) redisConnection -> {
            byte[] values = redisConnection.get(serializer.serialize(key));
            if (values == null) {
                return null;
            }
            return serializer.deserialize(values);
        });
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return redisTemplate.execute((RedisCallback<T>) redisConnection -> {
            String str = get(key);
            if (str == null) {
                return null;
            }
            return JSON.parseObject(str, clazz);
        });
    }

    @Override
    public Boolean exists(String key) {
        return redisTemplate.execute((RedisCallback<Boolean>) conn ->
                conn.exists(serializer.serialize(key)));
    }

    @Override
    public Long delete(String key) {
        return redisTemplate.execute((RedisCallback<Long>) conn ->
                conn.del(serializer.serialize(key)));
    }

    @Override
    public Boolean setNX(String key, String value) {
        return redisTemplate.execute((RedisCallback<Boolean>) conn ->
                conn.setNX(serializer.serialize(key), serializer.serialize(value)));
    }

    @Override
    public Void setEx(String key, String value, long seconds) {
        return redisTemplate.execute((RedisCallback<Void>) conn -> {
            byte[] keys = serializer.serialize(key);
            byte[] values = serializer.serialize(value);
            conn.setEx(keys, seconds, values);
            return null;
        });
    }

    @Override
    public Long incr(String key) {
        return redisTemplate.execute((RedisCallback<Long>) conn ->
                conn.incr(serializer.serialize(key)));
    }

    @Override
    public Double incrBy(String key, double value) {
        return redisTemplate.execute((RedisCallback<Double>) conn ->
                conn.incrBy(serializer.serialize(key), value));
    }

    @Override
    public Boolean tryLock(String key, String value, long timeout, TimeUnit unit) {
        byte[] keys = serializer.serialize(key);
        byte[] values = serializer.serialize(value);
        return redisTemplate.execute((RedisCallback<Boolean>) conn ->
                conn.set(keys, values, Expiration.from(timeout, unit), RedisStringCommands.SetOption.SET_IF_ABSENT));
    }
}

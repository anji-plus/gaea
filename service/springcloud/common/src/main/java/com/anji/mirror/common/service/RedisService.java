package com.anji.mirror.common.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisService {

    void setString(String key,String value);

    void setString(String key,String value,long expiresInSeconds);

    String getString(String key);

    void setObject(String key, Object value);

    void setObject(String key, Object value, long expiresInSeconds);

    Object getObject(String key);

    long incr(String key, long by, long expiresInSeconds);

    long incr(String key, long by);

    boolean exists(String key);

    void delete(String key);

    /**
     * 匹配前缀，批量删除
     * @param key
     */
    void deleteBatch(String key);

    long getExpire(String key);

    void rightPush(String key, String value, int expiresInSeconds);

    void hset(String hash, String hashKey, Object hashValue);

    void hincrement(String hash, String hashKey, long hashValue);

    Object hget(String hash, String hashKey);

    /**生成自动增长的id
     * @param key
     * @return
     */
    long getNextId(String key);

    Set<Object> getHashKeys(String key);

    Map<Object, Object> getHashEntries(String key);

    void deleteHashKeys(String key,Object ...hashKeys);

    List<String> hmget(String key, List hashKeys);

    Set<String> getKeysByPattern(String pattern);

    boolean setIfAbsent(String lockKey,Long time);
}

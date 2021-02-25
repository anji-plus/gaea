package com.anjiplus.template.common.service.cache;


import com.anjiplus.template.common.exception.CacheException;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * CacheService
 * </pre>
 *
 * @author
 * @version CacheService.java
 */
public interface CacheService {

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    boolean hasKey(String key) throws CacheException;

    /**
     * 设置对象过期时间
     *
     * @param key      缓存key
     * @param interval 过期时间，单位秒
     * @throws CacheException
     */
    void expire(String key, int interval) throws CacheException;

    /**
     * 设置对象过期时间
     *
     * @param key      缓存key
     * @param interval 过期时间
     * @param unit     时间单位
     * @throws CacheException
     */
    void expire(String key, int interval, TimeUnit unit) throws CacheException;

    /**
     * 添加对象到缓存
     *
     * @param key   缓存key
     * @param value 缓存对象
     * @throws CacheException
     */
    void add(String key, Serializable value) throws CacheException;

    /**
     * 添加对象到缓存
     *
     * @param key     缓存key
     * @param value   缓存对象
     * @param minutes 过期时间
     * @throws CacheException
     */
    void add(String key, Serializable value, int minutes) throws CacheException;

    /**
     * 添加对象到缓存
     *
     * @param key      缓存key
     * @param value    缓存对象
     * @param interval 过期时间
     * @param unit     时间单位
     * @throws CacheException
     */
    void add(String key, Serializable value, int interval, TimeUnit unit) throws CacheException;

    /**
     * 添加对象到缓存
     *
     * @param key      缓存key
     * @param value    缓存对象
     * @param interval 过期时间
     * @param unit     时间单位
     * @throws CacheException
     */
    void add(String key, Serializable value, long interval, TimeUnit unit) throws CacheException;

    /**
     * 获取缓存对象
     *
     * @param key 缓存key
     * @return
     * @throws CacheException
     */
    Serializable get(String key) throws CacheException;

    /**
     * 获取List缓存对象
     *
     * @param key   缓存key
     * @param start 开始位置
     * @param end   截止位置
     * @return
     * @throws CacheException
     */
    List<Serializable> getList(String key, long start, long end) throws CacheException;


    /**
     * 获取List缓存对象
     *
     * @param key 缓存key
     * @return
     * @throws CacheException
     */
    Long getListSize(String key) throws CacheException;

    /**
     * 模糊获取缓存对象
     *
     * @param keyPrefix 缓存keyPrefix
     * @return
     * @throws CacheException
     */
    List<Serializable> getLike(String keyPrefix) throws CacheException;

    /**
     * 模糊获取缓存对象
     *
     * @param keyPrefix 缓存keyPrefix
     * @return
     * @throws CacheException
     */
    Map<String, Object> getLikeToMap(String keyPrefix) throws CacheException;

    /**
     * 当且仅当 key 不存在，将 key 的值设为 value ，并返回 true ；若给定的 key 已经存在，则不做任何动作，并返回 false
     *
     * @param key      缓存key
     * @param value    缓存对象
     * @param interval 过期时间
     * @param unit     时间单位
     * @return 不存在, set 成功 = true;已经存在=false
     * @throws CacheException
     */
    boolean setNX(String key, Serializable value, int interval, TimeUnit unit) throws CacheException;

    /**
     * 删除缓存
     *
     * @param key 缓存key
     * @throws CacheException
     */
    void remove(String key) throws CacheException;

    /**
     * 批量删除
     *
     * @param keys
     * @throws CacheException
     */
    void remove(List<String> keys) throws CacheException;

    /**
     * 模糊删除缓存
     *
     * @param keyPrefix 缓存前缀
     * @throws CacheException
     */
    void removeLike(String keyPrefix) throws CacheException;

    /**
     * 添加集合到缓存
     *
     * @param key    缓存key
     * @param values 缓存对象
     * @throws CacheException
     */
    void addList(String key, Collection<Serializable> values) throws CacheException;

    /**
     * 添加集合到缓存
     *
     * @param key     缓存key
     * @param values  缓存对象
     * @param minutes 过期时间
     * @throws CacheException
     */
    void addList(String key, Collection<Serializable> values, int minutes) throws CacheException;

    /**
     * 计数器
     *
     * @param key 缓存key
     * @param val 计数器增量
     * @return
     * @throws CacheException
     */
    Long incrBy(String key, long val) throws CacheException;

    /**
     * 带过期时间的计数器，该过期时间的设置只有计数器的值为1的时候生效 请一定要注意
     *
     * @param key      缓存key
     * @param val      计数器增量
     * @param interval 间隔
     * @param unit     间隔单位
     * @return
     * @throws CacheException
     */
    Long incr(String key, long val, long interval, TimeUnit unit) throws CacheException;

    /**
     * 多选
     *
     * @param keys
     * @return
     */
    List<Serializable> multiGet(Collection<String> keys);

}

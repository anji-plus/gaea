package com.anjiplus.template.common.service.cache.impl;

import com.anjiplus.template.common.config.RedisCacheConfig;
import com.anjiplus.template.common.exception.CacheException;
import com.anjiplus.template.common.service.cache.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * CacheServiceImpl
 * </pre>
 *
 * @author
 * @version CacheServiceImpl.java,
 */
@Service
@ConditionalOnBean({RedisCacheConfig.class})
@Slf4j
public class CacheServiceImpl implements CacheService {

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    private static final int ZERO = 0;

    private static final int ONE = 1;

    private static final String STAR = "*";

    @Override
    public boolean hasKey(String key) throws CacheException {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CacheException(e, e.getMessage());
        }
    }


    @Override
    public void expire(String key, int interval) throws CacheException {
        try {
            redisTemplate.expire(key, interval, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CacheException(e, e.getMessage());
        }
    }

    @Override
    public void expire(String key, int interval, TimeUnit unit) {
        try {
            redisTemplate.expire(key, interval, unit);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CacheException(e, e.getMessage());
        }
    }

    @Override
    public void add(String key, Serializable value) throws CacheException {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new CacheException(ex, ex.getMessage());
        }
    }

    @Override
    public void add(String key, Serializable value, int minutes) throws CacheException {
        try {
            redisTemplate.opsForValue().set(key, value);
            redisTemplate.expire(key, minutes, TimeUnit.MINUTES);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new CacheException(ex, ex.getMessage());
        }
    }

    @Override
    public void add(String key, Serializable value, int interval, TimeUnit unit) throws CacheException {
        try {
            redisTemplate.opsForValue().set(key, value);
            redisTemplate.expire(key, interval, unit);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new CacheException(ex, ex.getMessage());
        }
    }

    @Override
    public void add(String key, Serializable value, long interval, TimeUnit unit) throws CacheException {
        try {
            redisTemplate.opsForValue().set(key, value);
            redisTemplate.expire(key, interval, unit);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new CacheException(ex, ex.getMessage());
        }
    }

    @Override
    public Serializable get(String key) throws CacheException {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception ex) {
            remove(key);
            log.error(ex.getMessage(), ex);
            return null;
        }
    }

    @Override
    public List<Serializable> getList(String key, long start, long end) throws CacheException {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception ex) {
            remove(key);
            log.error(ex.getMessage(), ex);
            return null;
        }
    }

    @Override
    public Long getListSize(String key) throws CacheException {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new CacheException(ex, ex.getMessage());
        }

    }

    @Override
    public boolean setNX(String key, Serializable value, int interval, TimeUnit unit) throws CacheException {
        boolean v = false;
        try {
            v = redisTemplate.opsForValue().setIfAbsent(key, value);
            if (v) {
                redisTemplate.expire(key, interval, unit);
            }
        } catch (Exception ex) {
            remove(key);
            log.error(ex.getMessage(), ex);
            return false;
        }
        return v;
    }

    @Override
    public void remove(String key) throws CacheException {
        try {
            redisTemplate.delete(key);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new CacheException(ex, ex.getMessage());
        }
    }

    @Override
    public void remove(List<String> keys) throws CacheException {
        try {
            redisTemplate.delete(keys);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new CacheException(ex, ex.getMessage());
        }
    }

    @Override
    public void removeLike(String keyPrefix) throws CacheException {
        try {
            if (StringUtils.isNotEmpty(keyPrefix)) {
//                Set<String> matchedCacheKeys = redisTemplate.keys(keyPrefix + STAR);
                Set<String> matchedCacheKeys = scan(keyPrefix);
                matchedCacheKeys.parallelStream().forEach(this::remove);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new CacheException(ex, ex.getMessage());
        }
    }

    @Override
    public void addList(String key, Collection<Serializable> values) throws CacheException {
        try {
            if (values != null && values.size() > ZERO) {
                remove(key);
                redisTemplate.opsForList().leftPushAll(key, values);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new CacheException(ex, ex.getMessage());
        }
    }

    @Override
    public Long incrBy(String key, long val) throws CacheException {
        try {
            return redisTemplate.opsForValue().increment(key, val);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new CacheException(ex, ex.getMessage());
        }
    }

    @Override
    public Long incr(String key, long val, long interval, TimeUnit unit) {
        Long result = null;
        try {
            result = this.incrBy(key, val);
            // val小于0表示回退，所以要忽略
            if (result == ONE && val > ZERO) {
                redisTemplate.expire(key, interval, unit);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new CacheException(ex, ex.getMessage());
        }
        return result;
    }

    @Override
    public List<Serializable> multiGet(Collection<String> keys) {
        try {
            return redisTemplate.opsForValue().multiGet(keys);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new CacheException(ex, ex.getMessage());
        }
    }

    @Override
    public void addList(String key, Collection<Serializable> values, int minutes) throws CacheException {
        try {
            remove(key);
            redisTemplate.opsForList().leftPushAll(key, values);
            redisTemplate.expire(key, minutes, TimeUnit.MINUTES);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new CacheException(ex, ex.getMessage());
        }
    }

    @Override
    public List<Serializable> getLike(String keyPrefix) throws CacheException {
        List<Serializable> list = new ArrayList<>();
        try {
            if (StringUtils.isNotEmpty(keyPrefix)) {
//                Set<String> matchedCacheKeys = redisTemplate.keys(keyPrefix + STAR);
//                for (String cacheKey : matchedCacheKeys) {
//                    list.add(redisTemplate.opsForValue().get(cacheKey));
//                }
                Set<String> matchedCacheKeys = scan(keyPrefix);
                matchedCacheKeys.stream().forEach(key -> list.add(redisTemplate.opsForValue().get(key)));
            }
        } catch (Exception ex) {
            removeLike(keyPrefix);
            log.error(ex.getMessage(), ex);
        }
        return list;
    }

    /**
     * @param keyPrefix
     * @return
     * @throws CacheException
     */
    @Override
    public Map<String, Object> getLikeToMap(String keyPrefix) {
        Map<String, Object> map = new HashMap<>(16);
        try {
            if (StringUtils.isNotEmpty(keyPrefix)) {
//                Set<String> matchedCacheKeys = redisTemplate.keys(keyPrefix + STAR);
//                for (String cacheKey : matchedCacheKeys) {
//                    map.put(cacheKey, redisTemplate.opsForValue().get(cacheKey));
//                }
                Set<String> matchedCacheKeys = scan(keyPrefix);
                matchedCacheKeys.stream().forEach(key -> map.put(key, redisTemplate.opsForValue().get(key)));
            }
        } catch (Exception ex) {
            removeLike(keyPrefix);
            log.error(ex.getMessage(), ex);
        }
        return map;
    }


    /**
     * 用scan 获取正则表达式 key,
     * 以避免用keys出问题
     *
     * @param keyPrefix
     * @return
     */
    private Set<String> scan(String keyPrefix) {
        return redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<String> keysTmp = new HashSet<>();
            Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(keyPrefix + STAR).count(1000).build());
            while (cursor.hasNext()) {
                keysTmp.add(new String(cursor.next()));
            }
            return keysTmp;
        });
    }

}

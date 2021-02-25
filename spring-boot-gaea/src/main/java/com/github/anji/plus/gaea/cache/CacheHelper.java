package com.github.anji.plus.gaea.cache;

import com.github.anji.plus.gaea.constant.GaeaConstant;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 缓存帮助类
 * @author lr
 * @since 2021-01-12
 */
public class CacheHelper {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 获取指定key的String类型缓存
     * @param key
     * @return
     */
    public String stringGet(String key) {
        String regKey = regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return "";
        }
        BoundValueOperations<String, String> operations = stringRedisTemplate.boundValueOps(regKey);
        return operations.get();
    }

    /**
     * 获取指定key的String类型缓存
     * @param key
     * @return
     */
    public Boolean setIfAbsent(String key, String value) {
        String regKey = regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return false;
        }
        BoundValueOperations<String, String> operations = stringRedisTemplate.boundValueOps(regKey);

        return operations.setIfAbsent(value);
    }


    /**
     * 增加1
     * @param key
     * @return
     */
    public Long increment(String key) {
        BoundValueOperations<String, String> operations = stringRedisTemplate.boundValueOps(key);
        return operations.increment();
    }

    /**
     * 设置失效时间
     * @param key
     * @param timeUnit
     * @param timeout
     */
    public void expire(String key, TimeUnit timeUnit , Long timeout) {
        stringRedisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * 增加1
     * @param key
     * @return
     */
    public Long increment(String key, Long step) {
        BoundValueOperations<String, String> operations = stringRedisTemplate.boundValueOps(key);
        return operations.increment(step);
    }

    /**
     * 是否存在指定KEY
     * @param key
     * @return
     */
    public boolean exist(String key) {
        return stringRedisTemplate.hasKey(key);
    }


    /**
     * 设置指定key的String类型缓存
     * @param key
     * @param value 缓存值
     * @return
     */
    public void stringSet(String key, String value) {
        String regKey = regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return;
        }
        BoundValueOperations<String, String> operations = stringRedisTemplate.boundValueOps(regKey);
        operations.set(value);
    }

    /**
     * 设置指定key的String类型缓存，包含过期时间
     * @param key
     * @param value
     * @param time
     * @param timeUnit 时间单位
     */
    public void stringSetExpire(String key, String value, long time, TimeUnit timeUnit) {
        String regKey = regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return;
        }
        BoundValueOperations<String, String> operations = stringRedisTemplate.boundValueOps(regKey);
        operations.set(value, time, timeUnit);
    }

    public String regKey(String key) {
        return RegExUtils.replaceAll(key, GaeaConstant.BLANK_REPLACE, GaeaConstant.BLANK);
    }


    /**
     * 设置指定key的String类型缓存，包含过期时间
     * @param key
     * @param value
     * @param seconds
     */
    public void stringSetExpire(String key, String value, long seconds) {
        stringSetExpire(key, value, seconds, TimeUnit.SECONDS);
    }
    /**
     * 获取指定key的hash缓存
     * @param key
     * @return
     */
    public Map<String, String> hashGet(String key) {
        BoundHashOperations<String, String, String> operations = stringRedisTemplate.boundHashOps(key);
        return operations.entries();
    }


    /**
     * 获取指定key的hash中对应的值
     * @param key
     * @param hashKey
     * @return
     */
    public String hashGetString(String key, String hashKey) {
        String regKey = regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return "";
        }
        String regHashKey = regKey(hashKey);
        if (StringUtils.isBlank(regHashKey)) {
            return "";
        }

        BoundHashOperations<String, String, String> operations = stringRedisTemplate.boundHashOps(regKey);
        if (hashKey.contains(GaeaConstant.SPLIT)) {
            String[] split = hashKey.split(GaeaConstant.SPLIT);
            String reduce = Arrays.stream(split).reduce("", (all, item) -> {
                if(StringUtils.isBlank(all)) {
                    all = operations.get(item);
                } else {
                    all = all+ "," + operations.get(item);
                }
                return all;
            });
            return reduce;
        }

        return operations.entries().get(regHashKey);
    }

    /**
     * 删除Hash中指定key的值
     * @param key
     * @param hashKey
     * @return
     */
    public void hashDel(String key, String hashKey) {
        String regKey = regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return;
        }
        String regHashKey = regKey(hashKey);
        if (StringUtils.isBlank(regHashKey)) {
            return;
        }
        BoundHashOperations<String, String, String> operations = stringRedisTemplate.boundHashOps(regKey);
        operations.delete(regHashKey);
    }

    /**
     * 判断指定key的hash中包含指定hashKey
     * @param key
     * @param hashKey
     * @return
     */
    public boolean hashExist(String key, String hashKey) {
        String regKey = regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return false;
        }
        String regHashKey = regKey(hashKey);
        if (StringUtils.isBlank(regHashKey)) {
            return false;
        }
        BoundHashOperations<String, String, String> operations = stringRedisTemplate.boundHashOps(regKey);
        return operations.hasKey(regHashKey);
    }

    /**
     * 判断指定key的hash中包含指定hashKeys中任何一个
     * @param key
     * @param hashKeys
     * @return
     */
    public boolean hashAnyExist(String key, String[] hashKeys) {
        String regKey = regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return false;
        }
        BoundHashOperations<String, String, String> operations = stringRedisTemplate.boundHashOps(regKey);
        for (String hashKey: hashKeys ) {
            if (operations.hasKey(hashKey)) {
                return true;
            }
        }
        return false;
    }



    /**
     * 设置指定key的hash缓存
     * @param key
     * @param hashKey
     * @param hashValue
     * @return
     */
    public void hashSet(String key, String hashKey, String hashValue) {
        String regKey = regKey(key);
        if (StringUtils.isBlank(regKey) || StringUtils.isBlank(hashKey)) {
            return;
        }
        String regHashKey = regKey(hashKey);
        if (StringUtils.isBlank(regHashKey)) {
            return;
        }
        BoundHashOperations<String, String, String> operations = stringRedisTemplate.boundHashOps(regKey);
        operations.put(regHashKey, hashValue);
    }


    /**
     * 设置指定key的hash缓存
     * @param hash
     * @return
     */
    public void hashSet(String key, Map<String, String> hash) {
        String regKey = regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return;
        }
        BoundHashOperations<String, String, String> operations = stringRedisTemplate.boundHashOps(regKey);
        operations.putAll(hash);
    }

    /**
     * 删除指定key
     * @param key
     * @return
     */
    public boolean delete(String key) {
        String regKey = regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return false;
        }
        return stringRedisTemplate.delete(key);
    }

    /**
     * 向集合中添加
     * @param key
     * @param values
     * @return
     */
    public Long setAdd(String key, String[] values) {
        return setAdd(key, values, false);
    }


    /**
     * 向集合中添加
     * @param key
     * @param values
     * @param clear 是否清空旧数据
     * @return
     */
    public Long setAdd(String key, String[] values, boolean clear) {
        if (clear) {
            stringRedisTemplate.delete(key);
        }
        if (values != null && values.length ==0) {
            return 0L;
        }
        BoundSetOperations<String, String> setOperations = stringRedisTemplate.boundSetOps(key);
        return setOperations.add(values);
    }

    /**
     * 返回对应key的集合
     * @param key
     * @return
     */
    public Set<String> setMembers(String key) {
        BoundSetOperations<String, String> setOperations = stringRedisTemplate.boundSetOps(key);
        return setOperations.members();
    }

    /**
     * 判断集合中是否有对应的value
     * @param key
     * @param value
     * @return
     */
    public Boolean setExist(String key, String value) {
        String regKey = regKey(key);
        if (StringUtils.isBlank(regKey)) {
            return false;
        }

        BoundSetOperations<String, String> setOperations = stringRedisTemplate.boundSetOps(regKey);
        return setOperations.isMember(value);
    }
}

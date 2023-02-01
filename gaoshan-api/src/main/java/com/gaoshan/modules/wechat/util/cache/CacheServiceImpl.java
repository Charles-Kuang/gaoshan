package com.gaoshan.modules.wechat.util.cache;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class CacheServiceImpl implements CacheService{
	private static Map<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

    /**
     * 存入缓存
     * @param key
     * @param cache
     */
    @Override
    public void putCache(String key, Cache cache) {
        caches.put(key, cache);
    }

    /**
     * 存入缓存
     * @param key
     * @param cache
     */
    @Override
    public void putCache(String key, Object datas, long timeOut) {
        timeOut = timeOut > 0 ? timeOut : 0L;
        putCache(key, new Cache(datas, timeOut, System.currentTimeMillis()));
    }

    /**
     * 获取对应缓存
     * @param key
     * @return
     */
    @Override
    public Cache getCacheByKey(String key) {
        if (this.isContains(key)) {
            return caches.get(key);
        }
        return null;
    }

    /**
     * 获取对应缓存
     * @param key
     * @return
     */
    @Override
    public <T> T getCacheDataByKey(String key,Class<T> clazz) {
        try{
            if (this.isContains(key)) {
                Object data=caches.get(key).getDatas();
                return JSON.parseObject(JSON.toJSONString(data),clazz);
            }
            return null;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 获取所有缓存
     * @param key
     * @return
     */
    @Override
    public Map<String, Cache> getCacheAll() {
        return caches;
    }

    /**
     * 判断是否在缓存中
     * @param key
     * @return
     */
    @Override
    public boolean isContains(String key) {
        return caches.containsKey(key);
    }

    /**
     * 清除所有缓存
     */
    @Override
    public void clearAll() {
        caches.clear();
    }

    /**
     * 清除对应缓存
     * @param key
     */
    @Override
    public void clearByKey(String key) {
        if (this.isContains(key)) {
            caches.remove(key);
        }
    }

    /**
     * 缓存是否超时失效
     * @param key
     * @return
     */
    @Override
    public boolean isTimeOut(String key) {
        if (!caches.containsKey(key)) {
            return true;
        }
        Cache cache = caches.get(key);
        long timeOut = cache.getTimeOut();
        long lastRefreshTime = cache.getLastRefeshTime();
        if (timeOut == 0 || System.currentTimeMillis() - lastRefreshTime >= timeOut) {
            return true;
        }
        return false;
    }

    /**
     * 获取所有key
     * @return
     */
    @Override
    public Set<String>  getAllKeys() {
        return caches.keySet();
    }
}

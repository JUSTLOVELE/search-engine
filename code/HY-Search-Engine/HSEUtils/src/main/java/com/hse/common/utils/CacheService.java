package com.hse.common.utils;
/**
 * @Description:缓存服务
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @author yangzl 2018-1-23
 * @version 1.00.00
 * @history:
 */


import com.hse.common.utils.enums.CacheTimeUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:缓存服务
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @author yangzl 2018-1-23
 * @version 1.00.00
 * @history:
 */
public class CacheService {

	private final static Map<String, CacheObject> map = new ConcurrentHashMap<String, CacheObject>();
	private final static Lock lock = new ReentrantLock();

	/**
     * 清理缓存
	 */
	public static void clear() {
		
		lock.lock();
		List<Object> keys = new ArrayList<Object>();
		
		for(Map.Entry<String, CacheObject> entry : map.entrySet()) {
			
			CacheObject cache = entry.getValue();
			
			if(cache.getEndTime() < System.currentTimeMillis()) {
				
				Object obj = cache.getCache();
				obj = null;
				cache = null;
				keys.add(entry.getKey());
			}
		}
		
		for(Object key: keys) {
			map.remove(key);
		}
		
		lock.unlock();
	}
	
	public static void put(String key, CacheObject value){
		map.put(key, value);
	}

	/**
     * 加入缓存,这里会自动生成CacheObject对象
	 * @param key
     * @param object
	 */
	public static void put(String key, Object object) {

		CacheObject cache = CacheObject.getCache(object, CacheTimeUnit.MINUTES, 120);//默认两小时缓存
		map.put(key, cache);
	}
	
	public static void remove(String key) {
		map.remove(key);
	}
	
	public static boolean containKey(String key) {
		return map.containsKey(key);
	}
	
	public static CacheObject get(String key){

		CacheObject cache = map.get(key);

		if(cache != null && cache.getEndTime() >= System.currentTimeMillis()) {
			return cache;
		}else if(cache != null && cache.getEndTime() < System.currentTimeMillis()){
			Object obj = cache.getCache();
			obj = null;
			cache = null;
			map.remove(key);
			return null;
		}else{
			return null;
		}
	}
}

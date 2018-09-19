package com.csy.o2o.service;

public interface CacheService {
	/**
	 * 删除'key'开头的key对应的键值对
	 * @param key
	 */
	void removeCacheByKey(String key);
}

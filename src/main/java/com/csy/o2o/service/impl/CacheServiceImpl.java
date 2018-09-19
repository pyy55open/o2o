package com.csy.o2o.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csy.o2o.cache.JedisUtil;
import com.csy.o2o.service.CacheService;

@Service
public class CacheServiceImpl implements CacheService{

	@Autowired
	JedisUtil.Keys jedisKeys;
	/**
	 * 删除key开头对应的键值对
	 */
	@Override
	public void removeCacheByKey(String key) {
		Set<String> set = jedisKeys.keys(key+"*");
		for (String keys : set) {
			jedisKeys.del(keys);
		}
	}

}

package com.chmei.nzbcommon.localcache;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * @author lixinjie
 * @since 2018-08-17
 */
public class GuavaLocalCache implements LocalCache {
	
	private Cache<String, Object> cache;
	
	public GuavaLocalCache(long duration, TimeUnit unit, long maxSize, int initCapacity) {
		cache = CacheBuilder.newBuilder()
				.expireAfterWrite(duration, unit)
				.maximumSize(maxSize)
				.initialCapacity(initCapacity)
				.build();
	}

	@Override
	public void put(String key, Object value) {
		cache.put(key, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V get(String key) {
		return (V)cache.getIfPresent(key);
	}

}

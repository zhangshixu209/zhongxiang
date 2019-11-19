package com.chmei.nzbcommon.localcache;

/**
 * @author lixinjie
 * @since 2018-08-08
 */
public interface LocalCache {
	
	void put(String key, Object value);
	
	<V> V get(String key);
}

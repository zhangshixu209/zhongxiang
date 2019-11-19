package com.chmei.nzbcommon.hierarchy;

/**
 * @author lixinjie
 * @since 2018-10-22
 */
public interface IHierarchy<T> {

	T getHigher();
	
	T getLower();
}

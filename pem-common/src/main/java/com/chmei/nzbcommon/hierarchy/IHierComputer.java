package com.chmei.nzbcommon.hierarchy;

import java.util.Collection;

/**
 * @author lixinjie
 * @since 2018-10-22
 */
public interface IHierComputer<T> {

	Collection<T> getDirect();
	
	Collection<? extends IHierarchy<T>> getAllHierarchy();
	
	Collection<T> getReachable();
	
	Collection<T> getAllReachable();
}

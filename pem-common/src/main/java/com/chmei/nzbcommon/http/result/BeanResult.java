package com.chmei.nzbcommon.http.result;

import java.util.List;

/**
 * @author lixinjie
 * @since 2018-07-19
 */
public class BeanResult extends JsonResult {

	public <T> T getBeanBody(Class<T> clazz) {
		return getBeanBody(clazz, getStringBody());
	}
	
	public <T> List<T> getBeansBody(Class<T> clazz) {
		return getBeansBody(clazz, getStringBody());
	}
}

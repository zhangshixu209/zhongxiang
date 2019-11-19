package com.chmei.nzbcommon.http;

import org.springframework.core.io.ByteArrayResource;

/**
 * @author lixinjie
 * @since 2018-07-19
 */
public enum ResponseClass {

	STRING(String.class),
	BYTES(byte[].class),
	//因RestTemplate在第678行返回响应后就会把底层的流关闭，
	//所以不能使用InputStreamResource从底层流中一点点读取
	STREAM(ByteArrayResource.class);
	
	private Class<?> clazz;
	
	private ResponseClass(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public Class<?> getClazz() {
		return clazz;
	}
}

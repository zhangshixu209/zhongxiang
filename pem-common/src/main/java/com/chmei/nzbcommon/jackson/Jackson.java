package com.chmei.nzbcommon.jackson;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author lixinjie
 * @since 2018-10-18
 */
public class Jackson {

	private static final ObjectMapper Object_Mapper = new ObjectMapper();
	
	static {
		Object_Mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		Object_Mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		Object_Mapper.setSerializationInclusion(Include.NON_NULL);
	}
	
	public static ObjectMapper getObjectMapper() {
		return Object_Mapper;
	}
}

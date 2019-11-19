package com.chmei.nzbcommon.http.param;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.chmei.nzbcommon.http.HttpParam;
import com.chmei.nzbcommon.http.RequestBodyType;

/**
 * @author lixinjie
 * @since 2018-05-16
 */
public class FormParam extends HttpParam {

	public FormParam() {
		super();
		setBody(new LinkedMultiValueMap<String, Object>());
		setRequestBodyType(RequestBodyType.FORM);
	}
	
	//只支持字符串类型的值
	public void addField(String name, String value) {
		addFieldInternal(name, value);
	}
	
	@SuppressWarnings("unchecked")
	protected void addFieldInternal(String name, Object value) {
		((MultiValueMap<String, Object>)getBody()).add(name, value);
	}
}

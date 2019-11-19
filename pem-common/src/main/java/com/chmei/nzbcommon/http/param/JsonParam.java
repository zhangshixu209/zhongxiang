package com.chmei.nzbcommon.http.param;

import com.chmei.nzbcommon.http.HttpParam;
import com.chmei.nzbcommon.http.RequestBodyType;
import com.chmei.nzbcommon.http.ResponseBodyType;

/**
 * @author lixinjie
 * @since 2018-07-19
 */
public class JsonParam extends HttpParam {

	public JsonParam() {
		super();
		setRequestBodyType(RequestBodyType.JSON);
		setResponseBodyType(ResponseBodyType.JSON);
	}
	
	public void setJsonBody(String json) {
		setBody(json);
	}
}

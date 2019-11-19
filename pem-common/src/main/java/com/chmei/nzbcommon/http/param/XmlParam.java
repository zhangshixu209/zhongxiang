package com.chmei.nzbcommon.http.param;

import com.chmei.nzbcommon.http.HttpParam;
import com.chmei.nzbcommon.http.RequestBodyType;
import com.chmei.nzbcommon.http.ResponseBodyType;

/**
 * @author lixinjie
 * @since 2018-07-19
 */
public class XmlParam extends HttpParam {

	public XmlParam() {
		super();
		setRequestBodyType(RequestBodyType.XML);
		setResponseBodyType(ResponseBodyType.XML);
	}
	
	public void setXmlBody(String xml) {
		setBody(xml);
	}
}

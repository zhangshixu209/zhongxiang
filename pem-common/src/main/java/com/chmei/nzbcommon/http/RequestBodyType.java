package com.chmei.nzbcommon.http;

/**
 * @author lixinjie
 * @since 2017-12-20
 */
public enum RequestBodyType {

	JSON("application/json"),
	XML("application/xml"),
	
	FORM("application/x-www-form-urlencoded"),
	FILE("multipart/form-data"),
	BEAN("multipart/form-data");
	
	private String contentType;
	private String charset = "utf-8";
	
	private RequestBodyType(String contentType) {
		this.contentType = contentType;
	}
	
	public RequestBodyType charset(String charset) {
		this.charset = charset;
		return this;
	}
	
	public String contentType() {
		return contentType + ";charset=" + charset;
	}

}

package com.chmei.nzbcommon.http;

/**
 * @author lixinjie
 * @since 2017-12-20
 */
public enum ResponseBodyType {

	HTML("text/html"),
	XHTML("application/xhtml+xml"),
	PLAIN("text/plain"),
	
	JSON("application/json"),
	XML("application/xml"),
	
	BEAN("application/json");
	
	private String contentType;
	private String charset = "utf-8";
	
	private ResponseBodyType(String contentType) {
		this.contentType = contentType;
	}
	
	public ResponseBodyType charset(String charset) {
		this.charset = charset;
		return this;
	}
	
	public String contentType() {
		return contentType + ";charset=" + charset;
	}

}

package com.chmei.nzbcommon.http;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lixinjie
 * @since 2017-12-19
 */
public class HttpParam {

	private String url;
	private String method;
	private Map<String, Object> uriVars = new HashMap<>();
	private Map<String, Object> httpHeaders = new HashMap<>();
	private Object body;
	private RequestBodyType requestBodyType;
	private ResponseBodyType responseBodyType;
	private ResponseClass responseClass = ResponseClass.STRING;

	public HttpParam() {
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<String, Object> getUriVars() {
		return uriVars;
	}

	public void setUriVars(Map<String, Object> uriVars) {
		this.uriVars = uriVars;
	}

	public Map<String, Object> getHttpHeaders() {
		return httpHeaders;
	}

	public void setHttpHeaders(Map<String, Object> httpHeaders) {
		this.httpHeaders = httpHeaders;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public RequestBodyType getRequestBodyType() {
		return requestBodyType;
	}

	public void setRequestBodyType(RequestBodyType requestBodyType) {
		this.requestBodyType = requestBodyType;
		if (requestBodyType != null) {
			addHttpHeader("Content-Type", requestBodyType.contentType());
		} else {
			removeHttpHeader("Content-Type");
		}
	}
	
	public ResponseBodyType getResponseBodyType() {
		return responseBodyType;
	}

	public void setResponseBodyType(ResponseBodyType responseBodyType) {
		this.responseBodyType = responseBodyType;
		if (responseBodyType != null) {
			addHttpHeader("Accept", responseBodyType.contentType());
		} else {
			removeHttpHeader("Accept");
		}
	}

	public ResponseClass getResponseClass() {
		return responseClass;
	}

	public void setResponseClass(ResponseClass responseClass) {
		this.responseClass = responseClass;
	}

	public void addUriVar(String name, Object value) {
		uriVars.put(name, value);
	}
	
	public void addHttpHeader(String name, Object value) {
		httpHeaders.put(name, value);
	}
	
	public void removeHttpHeader(String name) {
		httpHeaders.remove(name);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{url=").append(url).append(",uriVars=").append(uriVars);
		sb.append(",method=").append(method).append(",httpHeaders=").append(httpHeaders);
		sb.append(",body=").append(body).append(",requestBodyType=").append(requestBodyType);
		sb.append(",responseBodyType=").append(responseBodyType).append(",responseClass=").append(responseClass).append("}");
		return sb.toString();
	}
}

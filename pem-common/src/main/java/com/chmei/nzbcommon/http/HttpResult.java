package com.chmei.nzbcommon.http;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author lixinjie
 * @since 2017-12-19
 */
public class HttpResult {

	private static final Logger log = LoggerFactory.getLogger(HttpResult.class);
	
	private int statusCode;
	private String reasonPhrase;
	private Map<String, Object> httpHeaders = new HashMap<>();
	private Object body;
	
	public HttpResult() {
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getReasonPhrase() {
		return reasonPhrase;
	}

	public void setReasonPhrase(String reasonPhrase) {
		this.reasonPhrase = reasonPhrase;
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
	
	public void addHttpHeader(String name, Object value) {
		httpHeaders.put(name, value);
	}
	
	public boolean isOK() {
		return statusCode == 200;
	}
	
	protected byte[] getBytesBody(Object body) {
		if (body == null) {
			return null;
		}
		if (body instanceof byte[]) {
			return (byte[])body;
		}
		return null;
	}
	
	protected InputStream getStreamBody(Object body) {
		if (body == null) {
			return null;
		}
		try {
			if (body instanceof Resource) {
				return ((Resource)body).getInputStream();
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}
	
	protected String getStringBody(Object body) {
		if (body == null) {
			return null;
		}
		if (body instanceof String) {
			return (String)body;
		}
		return null;
	}
	
	protected <T> T getBeanBody(Class<T> clazz, String body) {
		if (body == null) {
			return null;
		}
		return JSON.parseObject(body, clazz);
	}
	
	protected <T> List<T> getBeansBody(Class<T> clazz, String body) {
		if (body == null) {
			return null;
		}
		return JSON.parseArray(body, clazz);
	}
	
	protected JSONObject getJsonObjectBody(String body) {
		if (body == null) {
			return null;
		}
		try {
			return JSON.parseObject(body);
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}
	
	protected JSONArray getJsonArrayBody(String body) {
		if (body == null) {
			return null;
		}
		try {
			return JSON.parseArray(body);
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}
	
	protected Element getXmlBody(String body) {
		if (body == null) {
			return null;
		}
		try {
			return DocumentHelper.parseText(body).getRootElement();
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{statusCode=").append(statusCode).append(",reasonPhrase=").append(reasonPhrase);
		sb.append(",httpHeaders=").append(httpHeaders).append(",body=").append(bodyString(body)).append("}");
		return sb.toString();
	}
	
	private String bodyString(Object body) {
		if (body == null) {
			return "@null";
		}
		if (body instanceof String) {
			return (String)body;
		} else if (body instanceof byte[]) {
			return "@byte[]";
		} else if (body instanceof Resource) {
			return "@stream";
		}
		return "@unknown";
	}
}

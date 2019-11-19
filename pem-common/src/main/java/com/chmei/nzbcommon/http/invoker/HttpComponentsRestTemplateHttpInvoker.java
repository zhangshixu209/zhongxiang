package com.chmei.nzbcommon.http.invoker;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.chmei.nzbcommon.http.HttpParam;
import com.chmei.nzbcommon.http.HttpResult;
import com.chmei.nzbcommon.http.ResponseBodyType;
import com.chmei.nzbcommon.http.ResponseClass;
import com.chmei.nzbcommon.http.result.BeanResult;
import com.chmei.nzbcommon.http.result.BytesResult;
import com.chmei.nzbcommon.http.result.JsonResult;
import com.chmei.nzbcommon.http.result.StreamResult;
import com.chmei.nzbcommon.http.result.StringResult;
import com.chmei.nzbcommon.http.result.XmlResult;

/**
 * 使用RestTemplate发送http请求
 * @author lixinjie
 * @since 2017-12-19
 */
public class HttpComponentsRestTemplateHttpInvoker implements IHttpInvoker {

	private static final Logger log = LoggerFactory.getLogger(HttpComponentsRestTemplateHttpInvoker.class);
	
	private int connectionRequestTimeout = 60 * 1000;
	private int connectTimeout = 60 * 1000;
	private int readTimeout = 120 * 1000;
	private int maxConnPerRoute = 256;
	private int maxConnTotal = 2560;
	private long maxIdleTime = 1200;
	
	private RestTemplate restTemplate;
	
	public HttpComponentsRestTemplateHttpInvoker() {
		initRestTemplate();
	}
	
	public HttpComponentsRestTemplateHttpInvoker(int connectionRequestTimeout, int connectTimeout,
			int readTimeout, int maxConnPerRoute, int maxConnTotal, long maxIdleTime) {
		this.connectionRequestTimeout = connectionRequestTimeout;
		this.connectTimeout = connectTimeout;
		this.readTimeout = readTimeout;
		this.maxConnPerRoute = maxConnPerRoute;
		this.maxConnTotal = maxConnTotal;
		this.maxIdleTime = maxIdleTime;
		initRestTemplate();
	}
	
	@Override
	public HttpResult invoke(HttpParam httpParam) {
		String uuid = UUID.randomUUID().toString().substring(24);
		try {
			log.info("({})HttpParam：{}", uuid, httpParam.toString());
			HttpHeaders headers = buildHttpHeaders(httpParam);
			HttpEntity<?> requestEntity = buildRequestEntity(httpParam, headers);
			long beginTime = System.currentTimeMillis();
			ResponseEntity<?> responseEntity = restTemplate.exchange(httpParam.getUrl(), HttpMethod.resolve(httpParam.getMethod().toUpperCase()), requestEntity, httpParam.getResponseClass().getClazz(), httpParam.getUriVars());
			long endTime = System.currentTimeMillis();
			HttpResult httpResult = buildHttpResult(httpParam, responseEntity);
			log.info("({}-{}ms)HttpResult：{}", uuid, (endTime - beginTime), httpResult.toString());
			return httpResult;
		} catch (Exception e) {
			HttpResult httpResult = buildHttpResult(e);
			log.info("({})HttpResult：{}", uuid, httpResult.toString());
			log.error("(" + uuid + ")RestTemplate Http调用发生异常", e);
			return httpResult;
		}
	}

	private void initRestTemplate() {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(buildHttpClient());
		configHttpComponentsClientHttpRequestFactory(clientHttpRequestFactory);
		restTemplate = new RestTemplate(clientHttpRequestFactory);
		modifyStringHttpMessageConverterDefaultCharset(restTemplate);
	}
	
	private void modifyStringHttpMessageConverterDefaultCharset(RestTemplate restTemplate) {
		List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof StringHttpMessageConverter) {
				((StringHttpMessageConverter)converter).setDefaultCharset(Charset.forName("UTF-8"));
			}
		}
	}
	
	private HttpHeaders buildHttpHeaders(HttpParam httpParam) {
		HttpHeaders headers = new HttpHeaders();
		addHttpHeaders(headers, httpParam);
		return headers;
	}
	
	private HttpEntity<?> buildRequestEntity(HttpParam httpParam, HttpHeaders headers) {
		return new HttpEntity<Object>(httpParam.getBody(), headers);
	}
	
	private HttpResult buildHttpResult(Exception exception) {
		HttpResult httpResult = new HttpResult();
		httpResult.setStatusCode(500);
		httpResult.setReasonPhrase(exception.getMessage());
		return httpResult;
	}
	
	private HttpResult buildHttpResult(HttpParam httpParam, ResponseEntity<?> responseEntity) {
		HttpResult httpResult = buildHttpResult(httpParam);
		httpResult.setStatusCode(responseEntity.getStatusCode().value());
		httpResult.setReasonPhrase(responseEntity.getStatusCode().getReasonPhrase());
		addHttpHeaders(responseEntity.getHeaders(), httpResult);
		httpResult.setBody(responseEntity.getBody());
		return httpResult;
	}
	
	private HttpResult buildHttpResult(HttpParam httpParam) {
		if (httpParam.getResponseClass() == ResponseClass.BYTES) {
			return new BytesResult();
		}
		if (httpParam.getResponseClass() == ResponseClass.STREAM) {
			return new StreamResult();
		}
		if (httpParam.getResponseClass() == ResponseClass.STRING) {
			if (httpParam.getResponseBodyType() == ResponseBodyType.JSON) {
				return new JsonResult();
			}
			if (httpParam.getResponseBodyType() == ResponseBodyType.BEAN) {
				return new BeanResult();
			}
			if (httpParam.getResponseBodyType() == ResponseBodyType.XML) {
				return new XmlResult();
			}
		}
		return new StringResult();
	}
	
	private void addHttpHeaders(HttpHeaders headers, HttpResult httpResult) {
		for (Map.Entry<String, String> header : headers.toSingleValueMap().entrySet()) {
			httpResult.addHttpHeader(header.getKey(), header.getValue());
		}
	}
	
	private void addHttpHeaders(HttpHeaders headers, HttpParam httpParam) {
		for (Map.Entry<String, Object> header : httpParam.getHttpHeaders().entrySet()) {
			headers.add(header.getKey(), header.getValue().toString());
		}
	}
	
	private CloseableHttpClient buildHttpClient() {
		return HttpClients.custom()
				.setMaxConnPerRoute(maxConnPerRoute)
				.setMaxConnTotal(maxConnTotal)
				.evictExpiredConnections()
				.evictIdleConnections(maxIdleTime, TimeUnit.SECONDS)
				.build();
	}
	
	private void configHttpComponentsClientHttpRequestFactory(HttpComponentsClientHttpRequestFactory clientHttpRequestFactory) {
		clientHttpRequestFactory.setBufferRequestBody(false);
		clientHttpRequestFactory.setConnectionRequestTimeout(connectionRequestTimeout);
		clientHttpRequestFactory.setConnectTimeout(connectTimeout);
		clientHttpRequestFactory.setReadTimeout(readTimeout);
	}
	
}

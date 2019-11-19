package com.chmei.nzbdata.autoconfigure.httpinvoker;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chmei.nzbcommon.http.invoker.HttpComponentsRestTemplateHttpInvoker;
import com.chmei.nzbcommon.http.invoker.IHttpInvoker;
import com.chmei.nzbcommon.http.invoker.OkHttp3RestTemplateHttpInvoker;
import com.chmei.nzbdata.autoconfigure.httpinvoker.HttpInvokerProperties.HttpComponentsEntry;
import com.chmei.nzbdata.autoconfigure.httpinvoker.HttpInvokerProperties.OkHttp3Entry;

/**
 * @author lixinjie
 * @since 2018-09-07
 */
@Configuration
@EnableConfigurationProperties(HttpInvokerProperties.class)
public class HttpInvokerAutoConfiguration {

	private HttpInvokerProperties httpInvokerProperties;
	
	public HttpInvokerAutoConfiguration(HttpInvokerProperties httpInvokerProperties) {
		this.httpInvokerProperties = httpInvokerProperties;
	}
	
	@Bean
	public IHttpInvoker httpcomponentsHttpInvoker() {
		HttpComponentsEntry entry = httpInvokerProperties.getHttpcomponents().get("httpcomponentsHttpInvoker");
		return new HttpComponentsRestTemplateHttpInvoker(entry.getConnectionRequestTimeout(),
				entry.getConnectTimeout(), entry.getReadTimeout(), entry.getMaxConnPerRoute(),
				entry.getMaxConnTotal(), entry.getMaxIdleTime());
	}
	
	//@Bean
	public IHttpInvoker okhttp3HttpInvoker() {
		OkHttp3Entry entry = httpInvokerProperties.getOkhttp3().get("okhttp3HttpInvoker");
		return new OkHttp3RestTemplateHttpInvoker(entry.getConnectTimeout(), entry.getReadTimeout(),
				entry.getWriteTimeout());
	}
}

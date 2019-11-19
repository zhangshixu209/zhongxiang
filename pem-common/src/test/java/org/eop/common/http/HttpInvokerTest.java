package org.eop.common.http;

import com.chmei.nzbcommon.http.HttpParam;
import com.chmei.nzbcommon.http.HttpResult;
import com.chmei.nzbcommon.http.ResponseClass;
import com.chmei.nzbcommon.http.invoker.HttpComponentsRestTemplateHttpInvoker;
import com.chmei.nzbcommon.http.invoker.IHttpInvoker;
import com.chmei.nzbcommon.http.invoker.OkHttp3RestTemplateHttpInvoker;
import com.chmei.nzbcommon.http.result.BytesResult;
import com.chmei.nzbcommon.http.result.StreamResult;
import com.chmei.nzbcommon.http.result.StringResult;

/**
 * @author lixinjie
 * @since 2018-07-19
 */
public class HttpInvokerTest {

	static void test1() {
		HttpParam httpParam = new HttpParam();
		httpParam.setUrl("https://www.baidu.com");
		httpParam.setMethod("GET");
		IHttpInvoker httpInvoker = new HttpComponentsRestTemplateHttpInvoker();
		HttpResult httpResult = httpInvoker.invoke(httpParam);
		System.out.println(httpResult instanceof StringResult);
		StringResult stringResult = (StringResult)httpResult;
		System.out.println(stringResult.getStringBody());
	}
	
	static void test2() {
		HttpParam httpParam = new HttpParam();
		httpParam.setUrl("https://www.baidu.com");
		httpParam.setMethod("GET");
		httpParam.setResponseClass(ResponseClass.BYTES);
		IHttpInvoker httpInvoker = new HttpComponentsRestTemplateHttpInvoker();
		HttpResult httpResult = httpInvoker.invoke(httpParam);
		System.out.println(httpResult instanceof BytesResult);
		BytesResult bytesResult = (BytesResult)httpResult;
		System.out.println(bytesResult.getBytesBody().length);
		System.out.println(new String(bytesResult.getBytesBody()));
	}
	
	static void test3() throws Exception {
		HttpParam httpParam = new HttpParam();
		httpParam.setUrl("https://www.baidu.com");
		httpParam.setMethod("GET");
		httpParam.setResponseClass(ResponseClass.STREAM);
		IHttpInvoker httpInvoker = new HttpComponentsRestTemplateHttpInvoker();
		HttpResult httpResult = httpInvoker.invoke(httpParam);
		System.out.println(httpResult instanceof StreamResult);
		StreamResult streamResult = (StreamResult)httpResult;
		System.out.println(streamResult.getStreamBody().available());
	}
	
	static void test4() {
		HttpParam httpParam = new HttpParam();
		httpParam.setUrl("https://www.baidu.com");
		httpParam.setMethod("GET");
		IHttpInvoker httpInvoker = new OkHttp3RestTemplateHttpInvoker();
		HttpResult httpResult = httpInvoker.invoke(httpParam);
		System.out.println(httpResult instanceof StringResult);
		StringResult stringResult = (StringResult)httpResult;
		System.out.println(stringResult.getStringBody());
	}
	
	static void test5() {
		HttpParam httpParam = new HttpParam();
		httpParam.setUrl("https://www.baidu.com");
		httpParam.setMethod("GET");
		httpParam.setResponseClass(ResponseClass.BYTES);
		IHttpInvoker httpInvoker = new OkHttp3RestTemplateHttpInvoker();
		HttpResult httpResult = httpInvoker.invoke(httpParam);
		System.out.println(httpResult instanceof BytesResult);
		BytesResult bytesResult = (BytesResult)httpResult;
		System.out.println(bytesResult.getBytesBody().length);
		System.out.println(new String(bytesResult.getBytesBody()));
	}
	
	static void test6() throws Exception {
		HttpParam httpParam = new HttpParam();
		httpParam.setUrl("https://www.baidu.com");
		httpParam.setMethod("GET");
		httpParam.setResponseClass(ResponseClass.STREAM);
		IHttpInvoker httpInvoker = new OkHttp3RestTemplateHttpInvoker();
		HttpResult httpResult = httpInvoker.invoke(httpParam);
		System.out.println(httpResult instanceof StreamResult);
		StreamResult streamResult = (StreamResult)httpResult;
		System.out.println(streamResult.getStreamBody().available());
	}
	
	public static void main(String[] args) throws Exception {
		test1();
		test2();
		test3();
		test4();
		test5();
		test6();
	}

}

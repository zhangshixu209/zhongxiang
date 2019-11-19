package com.chmei.nzbcommon.http.invoker;

import com.chmei.nzbcommon.http.HttpParam;
import com.chmei.nzbcommon.http.HttpResult;

/**
 * @author lixinjie
 * @since 2017-12-19
 */
public interface IHttpInvoker {

	HttpResult invoke(HttpParam httpParam);
}

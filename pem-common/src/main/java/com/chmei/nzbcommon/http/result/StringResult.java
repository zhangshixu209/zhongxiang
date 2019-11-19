package com.chmei.nzbcommon.http.result;

import com.chmei.nzbcommon.http.HttpResult;

/**
 * @author lixinjie
 * @since 2018-07-19
 */
public class StringResult extends HttpResult {

	public String getStringBody() {
		return getStringBody(getBody());
	}
}

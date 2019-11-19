package com.chmei.nzbcommon.http.result;

import java.io.InputStream;

import com.chmei.nzbcommon.http.HttpResult;

/**
 * @author lixinjie
 * @since 2018-07-19
 */
public class StreamResult extends HttpResult {

	public InputStream getStreamBody() {
		return getStreamBody(getBody());
	}
}

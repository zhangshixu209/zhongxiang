package com.chmei.nzbcommon.http.result;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author lixinjie
 * @since 2018-07-19
 */
public class JsonResult extends StringResult {

	public JSONObject getJsonObjectBody() {
		return getJsonObjectBody(getStringBody());
	}
	
	public JSONArray getJsonArrayBody() {
		return getJsonArrayBody(getStringBody());
	}
}

package com.chmei.nzbcommon.http.result;

import org.dom4j.Element;

/**
 * @author lixinjie
 * @since 2018-07-19
 */
public class XmlResult extends StringResult {

	public Element getXmlBody() {
		return getXmlBody(getStringBody());
	}
}

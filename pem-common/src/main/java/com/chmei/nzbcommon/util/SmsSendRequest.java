
package com.chmei.nzbcommon.util;

import java.util.Map;

/**  
 * ClassName:SmsSendRequest  
 * Function: TODO ADD FUNCTION. 
 * Date:     2018年8月3日 下午3:21:19 
 * @author   LiuYuanTao  
 * @version    
 * @since    JDK 1.7  
 */
public class SmsSendRequest {
	private SmsSendHeader header;
	private Map<String,Object> body;
	public SmsSendHeader getHeader() {
		return header;
	}
	public void setHeader(SmsSendHeader header) {
		this.header = header;
	}
	public Map<String, Object> getBody() {
		return body;
	}
	public void setBody(Map<String, Object> body) {
		this.body = body;
	}
}
  

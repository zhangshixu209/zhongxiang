
package com.chmei.nzbcommon.util;  
/**  
 * ClassName:SmsSendHeader  
 * Function: TODO ADD FUNCTION. 
 * Date:     2018年8月3日 下午3:18:06 
 * @author   LiuYuanTao  
 * @version    
 * @since    JDK 1.7  
 */
public class SmsSendHeader {
	private String appId;
	private String appkey;
	public String getAppkey() {
		return appkey;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	private String startTime;
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
}
  

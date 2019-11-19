package com.chmei.nzbmanage.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lixinjie
 * @since 2018-11-29
 */
@ConfigurationProperties(prefix = "reportmanage")
public class NzbManageProperties {

	private Integer sessionTimeout;
	private String loginNeedMsg;
	private Integer smsExpireSeconds;
	
	public Integer getSessionTimeout() {
		return sessionTimeout;
	}

	public void setSessionTimeout(Integer sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

	public String getLoginNeedMsg() {
		return loginNeedMsg;
	}

	public void setLoginNeedMsg(String loginNeedMsg) {
		this.loginNeedMsg = loginNeedMsg;
	}

	public Integer getSmsExpireSeconds() {
		return smsExpireSeconds;
	}

	public void setSmsExpireSeconds(Integer smsExpireSeconds) {
		this.smsExpireSeconds = smsExpireSeconds;
	}
	
}

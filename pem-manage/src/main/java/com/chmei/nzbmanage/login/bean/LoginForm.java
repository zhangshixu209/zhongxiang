package com.chmei.nzbmanage.login.bean;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
/**
 *  发包单位用户新增
 */
public class LoginForm implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**手机号*/
	@NotNull
	private String mobile;
	/**验证码*/
	@NotNull
	private String imgCode;
	/**密码*/
	@NotNull
	public String password;
	/**短信验证码*/
	public String msgCode;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getImgCode() {
		return imgCode;
	}
	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMsgCode() {
		return msgCode;
	}
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
	
	

}
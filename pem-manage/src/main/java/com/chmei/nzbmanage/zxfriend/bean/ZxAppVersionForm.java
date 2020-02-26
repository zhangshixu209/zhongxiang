package com.chmei.nzbmanage.zxfriend.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

import java.util.Date;

/**
 * 众享好友群管理Form
 *
 * @author zhangshixu
 * @since 2019年11月13日 11点42分
 */
public class ZxAppVersionForm extends BaseForm {

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 版本号
	 */
	private String appVersion;

	/**
	 * APP地址
	 */
	private String appUrl;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * extend1
	 */
	private String extend1;

	/**
	 * 创建人Id
	 */
	private Long crtUserId;

	/**
	 * 创建人姓名
	 */
	private String crtUserName;

	/**
	 * 创建时间
	 */
	private Date crtTime;

	private static final long serialVersionUID = 1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getExtend1() {
		return extend1;
	}

	public void setExtend1(String extend1) {
		this.extend1 = extend1;
	}

	public Long getCrtUserId() {
		return crtUserId;
	}

	public void setCrtUserId(Long crtUserId) {
		this.crtUserId = crtUserId;
	}

	public String getCrtUserName() {
		return crtUserName;
	}

	public void setCrtUserName(String crtUserName) {
		this.crtUserName = crtUserName;
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
}
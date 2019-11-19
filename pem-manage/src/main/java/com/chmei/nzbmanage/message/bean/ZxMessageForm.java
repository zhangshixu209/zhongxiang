package com.chmei.nzbmanage.message.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

import java.util.Date;

/**
 * 众享信息Form
 *
 * @author zhangshixu
 * @since 2019年11月11日 10点46分
 */
public class ZxMessageForm extends BaseForm {
	/**
	 * 众享信息ID
	 */
	private Long zxMessageId;

	/**
	 * 发布众享信息用户ID
	 */
	private String memberAccount;

	/**
	 * 众享信息链接
	 */
	private String zxMessageLinkUrl;

	/**
	 * 众享信息视频链接
	 */
	private String zxMessageVideoUrl;

	/**
	 * 众享信息发布时间
	 */
	private Date zxMessageDate;

	/**
	 * 浏览次数
	 */
	private Integer zxMessageViewCount;

	/**
	 * 众享信息文字
	 */
	private String zxMessageText;

	/**
	 * 众享信息图片地址
	 */
	private String filePaths;

	private static final long serialVersionUID = 1L;

	public Long getZxMessageId() {
		return zxMessageId;
	}

	public void setZxMessageId(Long zxMessageId) {
		this.zxMessageId = zxMessageId;
	}

	public String getMemberAccount() {
		return memberAccount;
	}

	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
	}

	public String getZxMessageLinkUrl() {
		return zxMessageLinkUrl;
	}

	public void setZxMessageLinkUrl(String zxMessageLinkUrl) {
		this.zxMessageLinkUrl = zxMessageLinkUrl;
	}

	public String getZxMessageVideoUrl() {
		return zxMessageVideoUrl;
	}

	public void setZxMessageVideoUrl(String zxMessageVideoUrl) {
		this.zxMessageVideoUrl = zxMessageVideoUrl;
	}

	public Date getZxMessageDate() {
		return zxMessageDate;
	}

	public void setZxMessageDate(Date zxMessageDate) {
		this.zxMessageDate = zxMessageDate;
	}

	public Integer getZxMessageViewCount() {
		return zxMessageViewCount;
	}

	public void setZxMessageViewCount(Integer zxMessageViewCount) {
		this.zxMessageViewCount = zxMessageViewCount;
	}

	public String getZxMessageText() {
		return zxMessageText;
	}

	public void setZxMessageText(String zxMessageText) {
		this.zxMessageText = zxMessageText;
	}

	public String getFilePaths() {
		return filePaths;
	}

	public void setFilePaths(String filePaths) {
		this.filePaths = filePaths;
	}
}
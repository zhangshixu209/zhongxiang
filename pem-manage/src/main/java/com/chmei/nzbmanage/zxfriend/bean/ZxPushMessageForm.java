package com.chmei.nzbmanage.zxfriend.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

import java.util.Date;

/**
 * 众享好友群管理Form
 *
 * @author zhangshixu
 * @since 2019年11月13日 11点42分
 */
public class ZxPushMessageForm extends BaseForm {

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 推送消息标题
	 */
	private Long messageTitle;

	/**
	 * 推送消息内容
	 */
	private String messageContent;

	/**
	 * 阅读状态（1未阅读，2已阅读）
	 */
	private String messageStatus;

	/**
	 * 消息类型（1001广告分红，1002众享公告，1003众享好友，1004投诉处理，1005提现审核，1006反馈回复）
	 */
	private String messageType;

	/**
	 * 消息接收人账号
	 */
	private String memberAccount;

	/**
	 * 备用
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

	public Long getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(Long messageTitle) {
		this.messageTitle = messageTitle;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMemberAccount() {
		return memberAccount;
	}

	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
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
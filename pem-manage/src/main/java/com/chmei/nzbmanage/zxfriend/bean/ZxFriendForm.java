package com.chmei.nzbmanage.zxfriend.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

import java.util.Date;

/**
 * 众享好友群管理Form
 *
 * @author zhangshixu
 * @since 2019年11月13日 11点42分
 */
public class ZxFriendForm extends BaseForm {

	/** 序列化 */
	private static final long serialVersionUID = 1L;

	/**
	 * 群组ID
	 */
	private String groupId;

	/**
	 * 群主账号
	 */
	private String memberAccount;

	/**
	 * 升级标准
	 */
	private Integer goal;

	/**
	 * 众享好友id
	 */
	private Long zxFriendId;

	/**
	 * 当前人账号
	 */
	private String zxFriendUserId;

	/**
	 * 好友备注名称
	 */
	private String zxFriendRemark;

	/**
	 * 好友账号
	 */
	private String zxFriendFriendId;

	/**
	 * 是否关注(Y,关注;N,非关注)
	 */
	private String zxFriendNotesType;

	/**
	 * 是否是好友(Y,是,N否)
	 */
	private String zxFriendFriendType;

	/**
	 * 关注好友时间
	 */
	private Date zxFriendNotesDate;

	/**
	 * 添加好友时间
	 */
	private Date zxFriendAddDate;

	/**
	 * 取消关注好友时间
	 */
	private Date zxFriendOffNotesDate;

	/**
	 * 删除好友时间
	 */
	private Date zxFriendDelDate;

	/**
	 * 众享好友分组id
	 */
	private Long zxFriendGroupingId;

	private String zxFriendGroupingName;

	/**
	 * 手机通讯录
	 */
	private String mobileContacts;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getMemberAccount() {
		return memberAccount;
	}

	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
	}

	public Integer getGoal() {
		return goal;
	}

	public void setGoal(Integer goal) {
		this.goal = goal;
	}

	public Long getZxFriendId() {
		return zxFriendId;
	}

	public void setZxFriendId(Long zxFriendId) {
		this.zxFriendId = zxFriendId;
	}

	public String getZxFriendUserId() {
		return zxFriendUserId;
	}

	public void setZxFriendUserId(String zxFriendUserId) {
		this.zxFriendUserId = zxFriendUserId;
	}

	public String getZxFriendRemark() {
		return zxFriendRemark;
	}

	public void setZxFriendRemark(String zxFriendRemark) {
		this.zxFriendRemark = zxFriendRemark;
	}

	public String getZxFriendFriendId() {
		return zxFriendFriendId;
	}

	public void setZxFriendFriendId(String zxFriendFriendId) {
		this.zxFriendFriendId = zxFriendFriendId;
	}

	public String getZxFriendNotesType() {
		return zxFriendNotesType;
	}

	public void setZxFriendNotesType(String zxFriendNotesType) {
		this.zxFriendNotesType = zxFriendNotesType;
	}

	public String getZxFriendFriendType() {
		return zxFriendFriendType;
	}

	public void setZxFriendFriendType(String zxFriendFriendType) {
		this.zxFriendFriendType = zxFriendFriendType;
	}

	public Date getZxFriendNotesDate() {
		return zxFriendNotesDate;
	}

	public void setZxFriendNotesDate(Date zxFriendNotesDate) {
		this.zxFriendNotesDate = zxFriendNotesDate;
	}

	public Date getZxFriendAddDate() {
		return zxFriendAddDate;
	}

	public void setZxFriendAddDate(Date zxFriendAddDate) {
		this.zxFriendAddDate = zxFriendAddDate;
	}

	public Date getZxFriendOffNotesDate() {
		return zxFriendOffNotesDate;
	}

	public void setZxFriendOffNotesDate(Date zxFriendOffNotesDate) {
		this.zxFriendOffNotesDate = zxFriendOffNotesDate;
	}

	public Date getZxFriendDelDate() {
		return zxFriendDelDate;
	}

	public void setZxFriendDelDate(Date zxFriendDelDate) {
		this.zxFriendDelDate = zxFriendDelDate;
	}

	public Long getZxFriendGroupingId() {
		return zxFriendGroupingId;
	}

	public void setZxFriendGroupingId(Long zxFriendGroupingId) {
		this.zxFriendGroupingId = zxFriendGroupingId;
	}

	public String getZxFriendGroupingName() {
		return zxFriendGroupingName;
	}

	public void setZxFriendGroupingName(String zxFriendGroupingName) {
		this.zxFriendGroupingName = zxFriendGroupingName;
	}

	public String getMobileContacts() {
		return mobileContacts;
	}

	public void setMobileContacts(String mobileContacts) {
		this.mobileContacts = mobileContacts;
	}
}
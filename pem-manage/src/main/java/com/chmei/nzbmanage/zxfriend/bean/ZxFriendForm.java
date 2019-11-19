package com.chmei.nzbmanage.zxfriend.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

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
}
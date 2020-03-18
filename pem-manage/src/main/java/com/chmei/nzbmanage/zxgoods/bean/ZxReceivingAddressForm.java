package com.chmei.nzbmanage.zxgoods.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

import java.util.Date;

/**
 * 商品分类管理Form
 *
 * @author zhangshixu
 * @since 2019年10月21日 16点52分
 */
public class ZxReceivingAddressForm extends BaseForm {
	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 用户账号
	 */
	private String memberAccount;

	/**
	 * 收货人姓名
	 */
	private String consigName;

	/**
	 * 收货人电话
	 */
	private String consigNamePhone;

	/**
	 * 收货地区
	 */
	private String consigArea;

	/**
	 * 收货人详细地址
	 */
	private String consigAddress;

	/**
	 * 是否默认（1是，0否）
	 */
	private String isDefault;

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

	public String getMemberAccount() {
		return memberAccount;
	}

	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
	}

	public String getConsigName() {
		return consigName;
	}

	public void setConsigName(String consigName) {
		this.consigName = consigName;
	}

	public String getConsigNamePhone() {
		return consigNamePhone;
	}

	public void setConsigNamePhone(String consigNamePhone) {
		this.consigNamePhone = consigNamePhone;
	}

	public String getConsigArea() {
		return consigArea;
	}

	public void setConsigArea(String consigArea) {
		this.consigArea = consigArea;
	}

	public String getConsigAddress() {
		return consigAddress;
	}

	public void setConsigAddress(String consigAddress) {
		this.consigAddress = consigAddress;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
}
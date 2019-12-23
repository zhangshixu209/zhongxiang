package com.chmei.nzbmanage.recharge.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值管理Form
 *
 * @author zhangshixu
 * @since 2019年10月29日 17点43分
 */
public class RechargeRecordForm extends BaseForm {
	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 会员账号
	 */
	private String memberAccount;

	/**
	 * 充值金额
	 */
	private BigDecimal rechargeAmount;

	/**
	 * 充值时间
	 */
	private Date rechargeTime;

	/**
	 * 充值类型（1001广告费充值，1002钱包余额充值，1003会员充值）
	 */
	private String status;

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

	/**
	 * 修改人Id
	 */
	private Long modfUserId;

	/**
	 * 修改人姓名
	 */
	private String modfUserName;

	/**
	 * 修改时间
	 */
	private Date modfUserTime;

	/**
	 * 会员状态
	 */
	private String memberStatus;

	private String ip;

	private String startTime;

	private String endTime;

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

	public BigDecimal getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(BigDecimal rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public Date getRechargeTime() {
		return rechargeTime;
	}

	public void setRechargeTime(Date rechargeTime) {
		this.rechargeTime = rechargeTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Long getModfUserId() {
		return modfUserId;
	}

	public void setModfUserId(Long modfUserId) {
		this.modfUserId = modfUserId;
	}

	public String getModfUserName() {
		return modfUserName;
	}

	public void setModfUserName(String modfUserName) {
		this.modfUserName = modfUserName;
	}

	public Date getModfUserTime() {
		return modfUserTime;
	}

	public void setModfUserTime(Date modfUserTime) {
		this.modfUserTime = modfUserTime;
	}

	public String getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
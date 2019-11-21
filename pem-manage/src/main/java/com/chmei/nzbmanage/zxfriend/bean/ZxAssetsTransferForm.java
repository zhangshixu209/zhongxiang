package com.chmei.nzbmanage.zxfriend.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资产转让Form
 *
 * @author zhangshixu
 * @since 2019年11月19日 14点50分
 */
public class ZxAssetsTransferForm extends BaseForm {

	/**
	 * 交易记录ID
	 */
	private Long dealId;

	/**
	 * 发布交易的人的ID账号
	 */
	private String dealUserId;

	/**
	 * 交易转让金额
	 */
	private BigDecimal dealMoney;

	/**
	 * 交易状态(0:取消;1:交易完成;2:刚刚发布没有任何状态)
	 */
	private String dealMoneyMark;

	/**
	 * 发布交易时间
	 */
	private Date delaDate;

	/**
	 * 会员账户
	 */
	private String memberAccount;

	/**
	 * 资产转让状态（1转让信息，2交易记录个人）
	 */
	private Integer transferType;

	/**
	 * 出售方账号
	 */
	private String fromMemberAccount;

	private static final long serialVersionUID = 1L;

	public Long getDealId() {
		return dealId;
	}

	public void setDealId(Long dealId) {
		this.dealId = dealId;
	}

	public String getDealUserId() {
		return dealUserId;
	}

	public void setDealUserId(String dealUserId) {
		this.dealUserId = dealUserId;
	}

	public BigDecimal getDealMoney() {
		return dealMoney;
	}

	public void setDealMoney(BigDecimal dealMoney) {
		this.dealMoney = dealMoney;
	}

	public String getDealMoneyMark() {
		return dealMoneyMark;
	}

	public void setDealMoneyMark(String dealMoneyMark) {
		this.dealMoneyMark = dealMoneyMark;
	}

	public Date getDelaDate() {
		return delaDate;
	}

	public void setDelaDate(Date delaDate) {
		this.delaDate = delaDate;
	}

	public String getMemberAccount() {
		return memberAccount;
	}

	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
	}

	public Integer getTransferType() {
		return transferType;
	}

	public void setTransferType(Integer transferType) {
		this.transferType = transferType;
	}

	public String getFromMemberAccount() {
		return fromMemberAccount;
	}

	public void setFromMemberAccount(String fromMemberAccount) {
		this.fromMemberAccount = fromMemberAccount;
	}
}
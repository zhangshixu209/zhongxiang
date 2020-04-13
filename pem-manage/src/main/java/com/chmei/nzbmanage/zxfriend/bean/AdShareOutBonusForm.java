package com.chmei.nzbmanage.zxfriend.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 广告分红Form
 *
 * @author zhangshixu
 * @since 2019年11月14日 10点05分
 */
public class AdShareOutBonusForm extends BaseForm {

	/**
	 * 广告分红ID
	 */
	private Long adShareOutBonusId;

	/**
	 * 用户ID
	 */
	private String adShareOutBonusUserId;

	/**
	 * 广告分红额度
	 */
	private BigDecimal adShareOutBonusLimit;

	/**
	 * 广告分红剩余额度
	 */
	private BigDecimal adShareOutBonusResidueLimit;

	/**
	 * 广告分红可用分红金额,(通过广告费进行追加过来)
	 */
	private BigDecimal adShareOutBonusMoney;

	/**
	 * 广告分红激活标志("Y" 是 ; "N" 否)
	 */
	private String adShareOutBonusType;

	/**
	 * 激活记录时间
	 */
	private Date adShareOutBonusDate;

	/**
	 * 用户账户
	 */
	private String memberAccount;

	/**
	 * 被推荐人账户
	 */
	private String coverMemberAccount;

	/**
	 * 追加额度
	 */
	private Integer money;

	/**
	 * 推荐人姓名
	 */
	private String userName;

	/**
	 * 分红周期
	 */
	private Integer date;

	private Long daySpan;

	private static final long serialVersionUID = 1L;

	public Long getAdShareOutBonusId() {
		return adShareOutBonusId;
	}

	public void setAdShareOutBonusId(Long adShareOutBonusId) {
		this.adShareOutBonusId = adShareOutBonusId;
	}

	public String getAdShareOutBonusUserId() {
		return adShareOutBonusUserId;
	}

	public void setAdShareOutBonusUserId(String adShareOutBonusUserId) {
		this.adShareOutBonusUserId = adShareOutBonusUserId;
	}

	public BigDecimal getAdShareOutBonusLimit() {
		return adShareOutBonusLimit;
	}

	public void setAdShareOutBonusLimit(BigDecimal adShareOutBonusLimit) {
		this.adShareOutBonusLimit = adShareOutBonusLimit;
	}

	public BigDecimal getAdShareOutBonusResidueLimit() {
		return adShareOutBonusResidueLimit;
	}

	public void setAdShareOutBonusResidueLimit(BigDecimal adShareOutBonusResidueLimit) {
		this.adShareOutBonusResidueLimit = adShareOutBonusResidueLimit;
	}

	public BigDecimal getAdShareOutBonusMoney() {
		return adShareOutBonusMoney;
	}

	public void setAdShareOutBonusMoney(BigDecimal adShareOutBonusMoney) {
		this.adShareOutBonusMoney = adShareOutBonusMoney;
	}

	public String getAdShareOutBonusType() {
		return adShareOutBonusType;
	}

	public void setAdShareOutBonusType(String adShareOutBonusType) {
		this.adShareOutBonusType = adShareOutBonusType;
	}

	public Date getAdShareOutBonusDate() {
		return adShareOutBonusDate;
	}

	public void setAdShareOutBonusDate(Date adShareOutBonusDate) {
		this.adShareOutBonusDate = adShareOutBonusDate;
	}

	public String getMemberAccount() {
		return memberAccount;
	}

	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
	}

	public String getCoverMemberAccount() {
		return coverMemberAccount;
	}

	public void setCoverMemberAccount(String coverMemberAccount) {
		this.coverMemberAccount = coverMemberAccount;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getDate() {
		return date;
	}

	public void setDate(Integer date) {
		this.date = date;
	}

	public Long getDaySpan() {
		return daySpan;
	}

	public void setDaySpan(Long daySpan) {
		this.daySpan = daySpan;
	}
}
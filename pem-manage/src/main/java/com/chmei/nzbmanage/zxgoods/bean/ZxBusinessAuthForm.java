package com.chmei.nzbmanage.zxgoods.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

import java.util.Date;

/**
 * 商家认证管理Form
 *
 * @author zhangshixu
 * @since 2019年10月21日 16点52分
 */
public class ZxBusinessAuthForm extends BaseForm {
	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 用户账号
	 */
	private String memberAccount;

	/**
	 * 商家类别（1个人，2公司）
	 */
	private String businessType;

	/**
	 * 公司名称
	 */
	private String businessName;

	/**
	 * 营业执照统一社会信用代码
	 */
	private String businessLicense;

	/**
	 * 身份证号码
	 */
	private String cardNum;

	/**
	 * 负责人
	 */
	private String leadingCadre;

	/**
	 * 联系方式
	 */
	private String phoneNumber;

	/**
	 * 身份证正反面照片
	 */
	private String cardNumUrl;

	/**
	 * 身份证正手持半身照片
	 */
	private String cardNumHalfUrl;

	/**
	 * 营业执照照片
	 */
	private String businessLicenseUrl;

	/**
	 * 法人身份证正手持照片
	 */
	private String legalCardNumUrl;

	/**
	 * 审核状态（1001待审核，1002审核通过，1003审核不通过）
	 */
	private String authStatus;

	/**
	 * 创建时间
	 */
	private Date crtTime;

	private String startTime;

	private String endTime;

	/**
	 * 审核状态
	 */
	private String auditOpinion;
	/**
	 * 商品秒杀窗口
	 */
	private String seckillWindow;
	/**
	 * 免费兑换窗口
	 */
	private String freeWindow;
	/**
	 * 幸运购物窗口
	 */
	private String luckyWindow;

	/**
	 * 开通窗口总费用
	 */
	private Integer openMoneyTotal;

	/**
	 * 商品分类
	 */
	private String goodsType;

	/**
	 * 取消窗口数量
	 */
	private String cancelReleaseWindow;

	private static final long serialVersionUID = 1L;

	public String getMemberAccount() {
		return memberAccount;
	}

	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getLeadingCadre() {
		return leadingCadre;
	}

	public void setLeadingCadre(String leadingCadre) {
		this.leadingCadre = leadingCadre;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCardNumUrl() {
		return cardNumUrl;
	}

	public void setCardNumUrl(String cardNumUrl) {
		this.cardNumUrl = cardNumUrl;
	}

	public String getCardNumHalfUrl() {
		return cardNumHalfUrl;
	}

	public void setCardNumHalfUrl(String cardNumHalfUrl) {
		this.cardNumHalfUrl = cardNumHalfUrl;
	}

	public String getBusinessLicenseUrl() {
		return businessLicenseUrl;
	}

	public void setBusinessLicenseUrl(String businessLicenseUrl) {
		this.businessLicenseUrl = businessLicenseUrl;
	}

	public String getLegalCardNumUrl() {
		return legalCardNumUrl;
	}

	public void setLegalCardNumUrl(String legalCardNumUrl) {
		this.legalCardNumUrl = legalCardNumUrl;
	}

	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
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

	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	public String getSeckillWindow() {
		return seckillWindow;
	}

	public void setSeckillWindow(String seckillWindow) {
		this.seckillWindow = seckillWindow;
	}

	public String getFreeWindow() {
		return freeWindow;
	}

	public void setFreeWindow(String freeWindow) {
		this.freeWindow = freeWindow;
	}

	public String getLuckyWindow() {
		return luckyWindow;
	}

	public void setLuckyWindow(String luckyWindow) {
		this.luckyWindow = luckyWindow;
	}

	public Integer getOpenMoneyTotal() {
		return openMoneyTotal;
	}

	public void setOpenMoneyTotal(Integer openMoneyTotal) {
		this.openMoneyTotal = openMoneyTotal;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getCancelReleaseWindow() {
		return cancelReleaseWindow;
	}

	public void setCancelReleaseWindow(String cancelReleaseWindow) {
		this.cancelReleaseWindow = cancelReleaseWindow;
	}
}
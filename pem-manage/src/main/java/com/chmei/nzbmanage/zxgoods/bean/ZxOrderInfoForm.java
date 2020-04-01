package com.chmei.nzbmanage.zxgoods.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

import java.util.Date;

/**
 * 订单信息管理Form
 *
 * @author zhangshixu
 * @since 2020年3月18日 18点54分
 */
public class ZxOrderInfoForm extends BaseForm {
	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 发货人账号
	 */
	private String sendGoodsAccount;

	/**
	 * 收货人账号
	 */
	private String consigGoodsAccount;

	/**
	 * 收货人姓名
	 */
	private String consigName;

	/**
	 * 收货人电话
	 */
	private String consigNamePhone;

	/**
	 * 收货人地址
	 */
	private String consigAddress;

	/**
	 * 快递名称
	 */
	private String expressName;

	/**
	 * 快递单号
	 */
	private String expressNumber;

	/**
	 * 订单状态（1001待发货，1002已发货，1003已收货）
	 */
	private String orderStatus;

	/**
	 * 创建时间
	 */
	private Date crtTime;

	private String memberAccount;

	/**
	 * 快递名称
	 */
	private String expCode;

	/**
	 * 快递单号
 	 */
	private String expNo;

	private String buyMemberAccount;

	private Long goodsId;

	private String goodsStatus;

	private static final long serialVersionUID = 1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSendGoodsAccount() {
		return sendGoodsAccount;
	}

	public void setSendGoodsAccount(String sendGoodsAccount) {
		this.sendGoodsAccount = sendGoodsAccount;
	}

	public String getConsigGoodsAccount() {
		return consigGoodsAccount;
	}

	public void setConsigGoodsAccount(String consigGoodsAccount) {
		this.consigGoodsAccount = consigGoodsAccount;
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

	public String getConsigAddress() {
		return consigAddress;
	}

	public void setConsigAddress(String consigAddress) {
		this.consigAddress = consigAddress;
	}

	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public String getExpressNumber() {
		return expressNumber;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	public String getMemberAccount() {
		return memberAccount;
	}

	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
	}

	public String getExpCode() {
		return expCode;
	}

	public void setExpCode(String expCode) {
		this.expCode = expCode;
	}

	public String getExpNo() {
		return expNo;
	}

	public void setExpNo(String expNo) {
		this.expNo = expNo;
	}

	public String getBuyMemberAccount() {
		return buyMemberAccount;
	}

	public void setBuyMemberAccount(String buyMemberAccount) {
		this.buyMemberAccount = buyMemberAccount;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}
}
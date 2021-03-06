package com.chmei.nzbmanage.zxgoods.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

import java.util.Date;

/**
 * 免费兑换管理Form
 *
 * @author zhangshixu
 * @since 2019年10月21日 16点52分
 */
public class ZxFreeGoodsForm extends BaseForm {
	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 发布者账号
	 */
	private String memberAccount;

	/**
	 * 商品封面地址
	 */
	private String productCoverUrl;

	/**
	 * 商品描述
	 */
	private String goodsDesc;

	/**
	 * 是否拥有网店（1001有网店，1002没有网店）
	 */
	private String isHaveShop;

	/**
	 * 商品链接地址
	 */
	private String goodsUrl;

	/**
	 * 商品说明图片地址
	 */
	private String goodsExplainImg;

	/**
	 * 商品包邮价格
	 */
	private Integer goodsParcelPrice;

	/**
	 * 个人所需广告费
	 */
	private Integer neededAdFee;

	/**
	 * 商品发布数量
	 */
	private Integer goodsReleaseNum;

	/**
	 * 商品剩余数量
	 */
	private Integer goodsSurplusNum;

	/**
	 * 商品分类ID
	 */
	private Long goodsTypeId;

	/**
	 * 商品状态（1001待审核，1002审核通过，1003审核不通过，1004上架，1005下架，1006已结束）
	 */
	private String goodsStatus;

	/**
	 * 创建时间
	 */
	private Date crtTime;

	/**
	 * 审核原因
	 */
	private String  auditOpinion;

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

	public String getProductCoverUrl() {
		return productCoverUrl;
	}

	public void setProductCoverUrl(String productCoverUrl) {
		this.productCoverUrl = productCoverUrl;
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public String getIsHaveShop() {
		return isHaveShop;
	}

	public void setIsHaveShop(String isHaveShop) {
		this.isHaveShop = isHaveShop;
	}

	public String getGoodsUrl() {
		return goodsUrl;
	}

	public void setGoodsUrl(String goodsUrl) {
		this.goodsUrl = goodsUrl;
	}

	public String getGoodsExplainImg() {
		return goodsExplainImg;
	}

	public void setGoodsExplainImg(String goodsExplainImg) {
		this.goodsExplainImg = goodsExplainImg;
	}

	public Integer getGoodsParcelPrice() {
		return goodsParcelPrice;
	}

	public void setGoodsParcelPrice(Integer goodsParcelPrice) {
		this.goodsParcelPrice = goodsParcelPrice;
	}

	public Integer getNeededAdFee() {
		return neededAdFee;
	}

	public void setNeededAdFee(Integer neededAdFee) {
		this.neededAdFee = neededAdFee;
	}

	public Integer getGoodsReleaseNum() {
		return goodsReleaseNum;
	}

	public void setGoodsReleaseNum(Integer goodsReleaseNum) {
		this.goodsReleaseNum = goodsReleaseNum;
	}

	public Integer getGoodsSurplusNum() {
		return goodsSurplusNum;
	}

	public void setGoodsSurplusNum(Integer goodsSurplusNum) {
		this.goodsSurplusNum = goodsSurplusNum;
	}

	public Long getGoodsTypeId() {
		return goodsTypeId;
	}

	public void setGoodsTypeId(Long goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}

	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
}
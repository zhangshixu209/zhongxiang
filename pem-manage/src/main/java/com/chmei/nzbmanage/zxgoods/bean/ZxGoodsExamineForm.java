package com.chmei.nzbmanage.zxgoods.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

import java.util.Date;

/**
 * 零元秒杀管理Form
 *
 * @author zhangshixu
 * @since 2019年10月21日 16点52分
 */
public class ZxGoodsExamineForm extends BaseForm {
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
	 * 所需参与总人数
	 */
	private Integer partakeNumber;

	/**
	 * 个人所需广告费
	 */
	private Integer neededAdFee;

	/**
	 * 总共所需广告费
	 */
	private Integer neededAdFeeTotal;

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
	 * 幸运数字
	 */
	private Integer luckyNumber;

	private Long goodsId;

	/**
	 * 商品分类
	 */
	private String goodsType;

	/**
	 * 审核原因
	 */
	private String  auditOpinion;

	private String buyMemberAccount;

	/**
	 * 轮次
	 */
	private Long rotationNum;

	/**
	 * 商品状态后台查询使用
	 */
	private String goodsStatusHT;

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

	public Integer getPartakeNumber() {
		return partakeNumber;
	}

	public void setPartakeNumber(Integer partakeNumber) {
		this.partakeNumber = partakeNumber;
	}

	public Integer getNeededAdFee() {
		return neededAdFee;
	}

	public void setNeededAdFee(Integer neededAdFee) {
		this.neededAdFee = neededAdFee;
	}

	public Integer getNeededAdFeeTotal() {
		return neededAdFeeTotal;
	}

	public void setNeededAdFeeTotal(Integer neededAdFeeTotal) {
		this.neededAdFeeTotal = neededAdFeeTotal;
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

	public Integer getLuckyNumber() {
		return luckyNumber;
	}

	public void setLuckyNumber(Integer luckyNumber) {
		this.luckyNumber = luckyNumber;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	public String getBuyMemberAccount() {
		return buyMemberAccount;
	}

	public void setBuyMemberAccount(String buyMemberAccount) {
		this.buyMemberAccount = buyMemberAccount;
	}

	public Long getRotationNum() {
		return rotationNum;
	}

	public void setRotationNum(Long rotationNum) {
		this.rotationNum = rotationNum;
	}

	public String getGoodsStatusHT() {
		return goodsStatusHT;
	}

	public void setGoodsStatusHT(String goodsStatusHT) {
		this.goodsStatusHT = goodsStatusHT;
	}
}
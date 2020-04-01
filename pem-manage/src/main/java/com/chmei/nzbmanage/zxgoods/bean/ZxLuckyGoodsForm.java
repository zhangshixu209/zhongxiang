package com.chmei.nzbmanage.zxgoods.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品分类管理Form
 *
 * @author zhangshixu
 * @since 2019年10月21日 16点52分
 */
public class ZxLuckyGoodsForm extends BaseForm {
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
	 * 商品市场价格
	 */
	private Integer goodsMarketPrice;

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
	 * 座位号
	 */
	private Long seatNum;

	/**
	 * 购买人账号
	 */
	private String buyMemberAccount;

	/**
	 * 幸运数字
	 */
	private String luckyNumber;

	/**
	 * 幸运序号
	 */
	private String luckyOrderNumber;

	/**
	 * 幸运用户标识（1幸运用户，2非幸运用户）
	 */
	private String goodsLuckStar;

	/**
	 * 购物金额
	 */
	private BigDecimal shoppingAmount;

	/**
	 * 活动标识1等待中、2已结束
	 */
	private String activityType;

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

	public Integer getGoodsMarketPrice() {
		return goodsMarketPrice;
	}

	public void setGoodsMarketPrice(Integer goodsMarketPrice) {
		this.goodsMarketPrice = goodsMarketPrice;
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

	public Long getSeatNum() {
		return seatNum;
	}

	public void setSeatNum(Long seatNum) {
		this.seatNum = seatNum;
	}

	public String getBuyMemberAccount() {
		return buyMemberAccount;
	}

	public void setBuyMemberAccount(String buyMemberAccount) {
		this.buyMemberAccount = buyMemberAccount;
	}

	public String getLuckyNumber() {
		return luckyNumber;
	}

	public void setLuckyNumber(String luckyNumber) {
		this.luckyNumber = luckyNumber;
	}

	public String getLuckyOrderNumber() {
		return luckyOrderNumber;
	}

	public void setLuckyOrderNumber(String luckyOrderNumber) {
		this.luckyOrderNumber = luckyOrderNumber;
	}

	public String getGoodsLuckStar() {
		return goodsLuckStar;
	}

	public void setGoodsLuckStar(String goodsLuckStar) {
		this.goodsLuckStar = goodsLuckStar;
	}

	public BigDecimal getShoppingAmount() {
		return shoppingAmount;
	}

	public void setShoppingAmount(BigDecimal shoppingAmount) {
		this.shoppingAmount = shoppingAmount;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
}
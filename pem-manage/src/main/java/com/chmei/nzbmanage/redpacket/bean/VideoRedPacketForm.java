package com.chmei.nzbmanage.redpacket.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 视频红包发布Form
 *
 * @author zhangshixu
 * @since 2019年11月05日 09点25分
 */
public class VideoRedPacketForm extends BaseForm {
	/**
	 * 众享红包视频红包id
	 */
	private Long redPacketVideoId;

	/**
	 * 用户id
	 */
	private String redPacketVideoUserId;

	/**
	 * 红包发布的金额大小
	 */
	private BigDecimal redPacketVideoMoneyCount;

	/**
	 * 红包发布的个数
	 */
	private Integer redPacketVideoCount;

	/**
	 * 红包剩余个数
	 */
	private Integer redPacketVideoStock;

	/**
	 * 红包剩余金额
	 */
	private BigDecimal redPacketVideoUnitAmount;

	/**
	 * 红包发布的详细信息
	 */
	private String redPacketVideoInfo;

	/**
	 * 手气最佳人的ID
	 */
	private String redPacketVideoLuckStar;

	/**
	 * 抢红包完记录开始时间
	 */
	private Date redPacketVideoStartTime;

	/**
	 * 抢红包完记录结束时间
	 */
	private Date redPacketVideoEndTime;

	/**
	 * 设置红包题目
	 */
	private String redPacketVideoQuestion;

	/**
	 * 设置红包题目选项
	 */
	private String redPacketVideoOptions;

	/**
	 * 设置红包题目答案
	 */
	private String redPacketVideoAnswer;

	/**
	 * 红包性别
	 */
	private Integer redPacketVideoSex;

	/**
	 * 红包开始年龄
	 */
	private Integer redPacketVideoAgeStart;

	/**
	 * 红包结束年龄
	 */
	private Integer redPacketVideoAgeEnd;

	/**
	 * 红包记录时间
	 */
	private Date redPacketVideoDate;

	/**
	 * 红包类型（0钱包，1红包，2广告费）
	 */
	private String redPacketVideoType;

	/**
	 * 会员账户
	 */
	private String memberAccount;

	/**
	 * 抢红包用户ID
	 */
	private String robUserId;

	/**
	 * 省名称
	 */
	private String provName;
	/**
	 * 市名称
	 */
	private String cityName;
	/**
	 * 区/县名称
	 */
	private String countyName;

	private static final long serialVersionUID = 1L;

	public Long getRedPacketVideoId() {
		return redPacketVideoId;
	}

	public void setRedPacketVideoId(Long redPacketVideoId) {
		this.redPacketVideoId = redPacketVideoId;
	}

	public String getRedPacketVideoUserId() {
		return redPacketVideoUserId;
	}

	public void setRedPacketVideoUserId(String redPacketVideoUserId) {
		this.redPacketVideoUserId = redPacketVideoUserId;
	}

	public BigDecimal getRedPacketVideoMoneyCount() {
		return redPacketVideoMoneyCount;
	}

	public void setRedPacketVideoMoneyCount(BigDecimal redPacketVideoMoneyCount) {
		this.redPacketVideoMoneyCount = redPacketVideoMoneyCount;
	}

	public Integer getRedPacketVideoCount() {
		return redPacketVideoCount;
	}

	public void setRedPacketVideoCount(Integer redPacketVideoCount) {
		this.redPacketVideoCount = redPacketVideoCount;
	}

	public Integer getRedPacketVideoStock() {
		return redPacketVideoStock;
	}

	public void setRedPacketVideoStock(Integer redPacketVideoStock) {
		this.redPacketVideoStock = redPacketVideoStock;
	}

	public BigDecimal getRedPacketVideoUnitAmount() {
		return redPacketVideoUnitAmount;
	}

	public void setRedPacketVideoUnitAmount(BigDecimal redPacketVideoUnitAmount) {
		this.redPacketVideoUnitAmount = redPacketVideoUnitAmount;
	}

	public String getRedPacketVideoInfo() {
		return redPacketVideoInfo;
	}

	public void setRedPacketVideoInfo(String redPacketVideoInfo) {
		this.redPacketVideoInfo = redPacketVideoInfo;
	}

	public String getRedPacketVideoLuckStar() {
		return redPacketVideoLuckStar;
	}

	public void setRedPacketVideoLuckStar(String redPacketVideoLuckStar) {
		this.redPacketVideoLuckStar = redPacketVideoLuckStar;
	}

	public Date getRedPacketVideoStartTime() {
		return redPacketVideoStartTime;
	}

	public void setRedPacketVideoStartTime(Date redPacketVideoStartTime) {
		this.redPacketVideoStartTime = redPacketVideoStartTime;
	}

	public Date getRedPacketVideoEndTime() {
		return redPacketVideoEndTime;
	}

	public void setRedPacketVideoEndTime(Date redPacketVideoEndTime) {
		this.redPacketVideoEndTime = redPacketVideoEndTime;
	}

	public String getRedPacketVideoQuestion() {
		return redPacketVideoQuestion;
	}

	public void setRedPacketVideoQuestion(String redPacketVideoQuestion) {
		this.redPacketVideoQuestion = redPacketVideoQuestion;
	}

	public String getRedPacketVideoOptions() {
		return redPacketVideoOptions;
	}

	public void setRedPacketVideoOptions(String redPacketVideoOptions) {
		this.redPacketVideoOptions = redPacketVideoOptions;
	}

	public String getRedPacketVideoAnswer() {
		return redPacketVideoAnswer;
	}

	public void setRedPacketVideoAnswer(String redPacketVideoAnswer) {
		this.redPacketVideoAnswer = redPacketVideoAnswer;
	}

	public Integer getRedPacketVideoSex() {
		return redPacketVideoSex;
	}

	public void setRedPacketVideoSex(Integer redPacketVideoSex) {
		this.redPacketVideoSex = redPacketVideoSex;
	}

	public Integer getRedPacketVideoAgeStart() {
		return redPacketVideoAgeStart;
	}

	public void setRedPacketVideoAgeStart(Integer redPacketVideoAgeStart) {
		this.redPacketVideoAgeStart = redPacketVideoAgeStart;
	}

	public Integer getRedPacketVideoAgeEnd() {
		return redPacketVideoAgeEnd;
	}

	public void setRedPacketVideoAgeEnd(Integer redPacketVideoAgeEnd) {
		this.redPacketVideoAgeEnd = redPacketVideoAgeEnd;
	}

	public Date getRedPacketVideoDate() {
		return redPacketVideoDate;
	}

	public void setRedPacketVideoDate(Date redPacketVideoDate) {
		this.redPacketVideoDate = redPacketVideoDate;
	}

	public String getRedPacketVideoType() {
		return redPacketVideoType;
	}

	public void setRedPacketVideoType(String redPacketVideoType) {
		this.redPacketVideoType = redPacketVideoType;
	}

	public String getMemberAccount() {
		return memberAccount;
	}

	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
	}

	public String getRobUserId() {
		return robUserId;
	}

	public void setRobUserId(String robUserId) {
		this.robUserId = robUserId;
	}

	public String getProvName() {
		return provName;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
}
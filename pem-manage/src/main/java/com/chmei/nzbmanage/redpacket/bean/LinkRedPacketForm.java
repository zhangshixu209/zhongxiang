package com.chmei.nzbmanage.redpacket.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 链接红包发布Form
 *
 * @author zhangshixu
 * @since 2019年11月05日 09点25分
 */
public class LinkRedPacketForm implements Serializable {
    /**
     * 众享红包链接红包id
     */
    private Long redPacketLinkId;

    /**
     * 用户账户
     */
    private String memberAccount;

    /**
     * 红包发布的金额大小
     */
    private BigDecimal redPacketLinkMoneyCount;

    /**
     * 红包发布的个数
     */
    private Integer redPacketLinkCount;

    /**
     * 红包剩余个数
     */
    private Integer redPacketLinkStock;

    /**
     * 红包剩余金额
     */
    private BigDecimal redPacketLinkUnitAmount;

    /**
     * 红包发布的详细信息
     */
    private String redPacketLinkInfo;

    /**
     * 手气最佳人的ID
     */
    private String redPacketLinkLuckStar;

    /**
     * 抢红包完记录开始时间
     */
    private Date redPacketLinkStartTime;

    /**
     * 抢红包完记录结束时间
     */
    private Date redPacketLinkEndTime;

    /**
     * 设置红包题目
     */
    private String redPacketLinkQuestion;

    /**
     * 设置红包题目选项
     */
    private String redPacketLinkOptions;

    /**
     * 设置红包题目答案
     */
    private String redPacketLinkAnswer;

    /**
     * 红包性别
     */
    private Integer redPacketLinkSex;

    /**
     * 红包开始年龄
     */
    private Integer redPacketLinkAgeStart;

    /**
     * 红包结束年龄
     */
    private Integer redPacketLinkAgeEnd;

    /**
     * 红包记录时间
     */
    private Date redPacketLinkDate;

    /**
     * 红包类型（0钱包，1红包，2广告费）
     */
    private String redPacketLinkType;

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

    /**
     * 红包链接
     */
    private String redPacketLinkUrl;

    private static final long serialVersionUID = 1L;

    public Long getRedPacketLinkId() {
        return redPacketLinkId;
    }

    public void setRedPacketLinkId(Long redPacketLinkId) {
        this.redPacketLinkId = redPacketLinkId;
    }

    public BigDecimal getRedPacketLinkMoneyCount() {
        return redPacketLinkMoneyCount;
    }

    public void setRedPacketLinkMoneyCount(BigDecimal redPacketLinkMoneyCount) {
        this.redPacketLinkMoneyCount = redPacketLinkMoneyCount;
    }

    public Integer getRedPacketLinkCount() {
        return redPacketLinkCount;
    }

    public void setRedPacketLinkCount(Integer redPacketLinkCount) {
        this.redPacketLinkCount = redPacketLinkCount;
    }

    public Integer getRedPacketLinkStock() {
        return redPacketLinkStock;
    }

    public void setRedPacketLinkStock(Integer redPacketLinkStock) {
        this.redPacketLinkStock = redPacketLinkStock;
    }

    public BigDecimal getRedPacketLinkUnitAmount() {
        return redPacketLinkUnitAmount;
    }

    public void setRedPacketLinkUnitAmount(BigDecimal redPacketLinkUnitAmount) {
        this.redPacketLinkUnitAmount = redPacketLinkUnitAmount;
    }

    public String getRedPacketLinkInfo() {
        return redPacketLinkInfo;
    }

    public void setRedPacketLinkInfo(String redPacketLinkInfo) {
        this.redPacketLinkInfo = redPacketLinkInfo;
    }

    public String getRedPacketLinkLuckStar() {
        return redPacketLinkLuckStar;
    }

    public void setRedPacketLinkLuckStar(String redPacketLinkLuckStar) {
        this.redPacketLinkLuckStar = redPacketLinkLuckStar;
    }

    public Date getRedPacketLinkStartTime() {
        return redPacketLinkStartTime;
    }

    public void setRedPacketLinkStartTime(Date redPacketLinkStartTime) {
        this.redPacketLinkStartTime = redPacketLinkStartTime;
    }

    public Date getRedPacketLinkEndTime() {
        return redPacketLinkEndTime;
    }

    public void setRedPacketLinkEndTime(Date redPacketLinkEndTime) {
        this.redPacketLinkEndTime = redPacketLinkEndTime;
    }

    public String getRedPacketLinkQuestion() {
        return redPacketLinkQuestion;
    }

    public void setRedPacketLinkQuestion(String redPacketLinkQuestion) {
        this.redPacketLinkQuestion = redPacketLinkQuestion;
    }

    public String getRedPacketLinkOptions() {
        return redPacketLinkOptions;
    }

    public void setRedPacketLinkOptions(String redPacketLinkOptions) {
        this.redPacketLinkOptions = redPacketLinkOptions;
    }

    public String getRedPacketLinkAnswer() {
        return redPacketLinkAnswer;
    }

    public void setRedPacketLinkAnswer(String redPacketLinkAnswer) {
        this.redPacketLinkAnswer = redPacketLinkAnswer;
    }

    public Integer getRedPacketLinkSex() {
        return redPacketLinkSex;
    }

    public void setRedPacketLinkSex(Integer redPacketLinkSex) {
        this.redPacketLinkSex = redPacketLinkSex;
    }

    public Integer getRedPacketLinkAgeStart() {
        return redPacketLinkAgeStart;
    }

    public void setRedPacketLinkAgeStart(Integer redPacketLinkAgeStart) {
        this.redPacketLinkAgeStart = redPacketLinkAgeStart;
    }

    public Integer getRedPacketLinkAgeEnd() {
        return redPacketLinkAgeEnd;
    }

    public void setRedPacketLinkAgeEnd(Integer redPacketLinkAgeEnd) {
        this.redPacketLinkAgeEnd = redPacketLinkAgeEnd;
    }

    public Date getRedPacketLinkDate() {
        return redPacketLinkDate;
    }

    public void setRedPacketLinkDate(Date redPacketLinkDate) {
        this.redPacketLinkDate = redPacketLinkDate;
    }

    public String getRedPacketLinkType() {
        return redPacketLinkType;
    }

    public void setRedPacketLinkType(String redPacketLinkType) {
        this.redPacketLinkType = redPacketLinkType;
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

    public String getRedPacketLinkUrl() {
        return redPacketLinkUrl;
    }

    public void setRedPacketLinkUrl(String redPacketLinkUrl) {
        this.redPacketLinkUrl = redPacketLinkUrl;
    }
}
package com.chmei.nzbmanage.redpacket.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 图文红包发布Form
 *
 * @author zhangshixu
 * @since 2019年11月05日 09点25分
 */
public class ImgRedPacketForm implements Serializable {
    /**
     * 众享红包图文id
     */
    private Long redPacketImgId;

    /**
     * 用户账户
     */
    private String memberAccount;

    /**
     * 红包发布的金额大小
     */
    private BigDecimal redPacketImgMoneyCount;

    /**
     * 红包发布的个数
     */
    private Integer redPacketImgCount;

    /**
     * 红包剩余个数
     */
    private Integer redPacketImgStock;

    /**
     * 图文红包图片链接
     */
    private String redPacketImgUrl;

    /**
     * 图文红包发布的详细说明信息
     */
    private String redPacketImgInfo;

    /**
     * 手气最佳人的ID
     */
    private String redPacketImgLuckStar;

    /**
     * 抢红包完记录开始时间
     */
    private Date redPacketImgStartTime;

    /**
     * 抢红包完记录结束时间
     */
    private Date redPacketImgEndTime;

    /**
     * 设置红包题目
     */
    private String redPacketImgQuestion;

    /**
     * 设置红包题目选项
     */
    private String redPacketImgOptions;

    /**
     * 设置红包题目答案
     */
    private String redPacketImgAnswer;

    /**
     * 红包性别
     */
    private Integer redPacketImgSex;

    /**
     * 红包开始年龄
     */
    private Integer redPacketImgAgeStart;

    /**
     * 红包结束年龄
     */
    private Integer redPacketImgAgeEnd;

    /**
     * 红包记录时间
     */
    private Date redPacketImgDate;

    /**
     * 红包类型（0钱包，1红包，2广告费）
     */
    private String redPacketImgType;

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

    public Long getRedPacketImgId() {
        return redPacketImgId;
    }

    public void setRedPacketImgId(Long redPacketImgId) {
        this.redPacketImgId = redPacketImgId;
    }

    public String getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(String memberAccount) {
        this.memberAccount = memberAccount;
    }

    public BigDecimal getRedPacketImgMoneyCount() {
        return redPacketImgMoneyCount;
    }

    public void setRedPacketImgMoneyCount(BigDecimal redPacketImgMoneyCount) {
        this.redPacketImgMoneyCount = redPacketImgMoneyCount;
    }

    public Integer getRedPacketImgCount() {
        return redPacketImgCount;
    }

    public void setRedPacketImgCount(Integer redPacketImgCount) {
        this.redPacketImgCount = redPacketImgCount;
    }

    public Integer getRedPacketImgStock() {
        return redPacketImgStock;
    }

    public void setRedPacketImgStock(Integer redPacketImgStock) {
        this.redPacketImgStock = redPacketImgStock;
    }

    public String getRedPacketImgUrl() {
        return redPacketImgUrl;
    }

    public void setRedPacketImgUrl(String redPacketImgUrl) {
        this.redPacketImgUrl = redPacketImgUrl;
    }

    public String getRedPacketImgInfo() {
        return redPacketImgInfo;
    }

    public void setRedPacketImgInfo(String redPacketImgInfo) {
        this.redPacketImgInfo = redPacketImgInfo;
    }

    public String getRedPacketImgLuckStar() {
        return redPacketImgLuckStar;
    }

    public void setRedPacketImgLuckStar(String redPacketImgLuckStar) {
        this.redPacketImgLuckStar = redPacketImgLuckStar;
    }

    public Date getRedPacketImgStartTime() {
        return redPacketImgStartTime;
    }

    public void setRedPacketImgStartTime(Date redPacketImgStartTime) {
        this.redPacketImgStartTime = redPacketImgStartTime;
    }

    public Date getRedPacketImgEndTime() {
        return redPacketImgEndTime;
    }

    public void setRedPacketImgEndTime(Date redPacketImgEndTime) {
        this.redPacketImgEndTime = redPacketImgEndTime;
    }

    public String getRedPacketImgQuestion() {
        return redPacketImgQuestion;
    }

    public void setRedPacketImgQuestion(String redPacketImgQuestion) {
        this.redPacketImgQuestion = redPacketImgQuestion;
    }

    public String getRedPacketImgOptions() {
        return redPacketImgOptions;
    }

    public void setRedPacketImgOptions(String redPacketImgOptions) {
        this.redPacketImgOptions = redPacketImgOptions;
    }

    public String getRedPacketImgAnswer() {
        return redPacketImgAnswer;
    }

    public void setRedPacketImgAnswer(String redPacketImgAnswer) {
        this.redPacketImgAnswer = redPacketImgAnswer;
    }

    public Integer getRedPacketImgSex() {
        return redPacketImgSex;
    }

    public void setRedPacketImgSex(Integer redPacketImgSex) {
        this.redPacketImgSex = redPacketImgSex;
    }

    public Integer getRedPacketImgAgeStart() {
        return redPacketImgAgeStart;
    }

    public void setRedPacketImgAgeStart(Integer redPacketImgAgeStart) {
        this.redPacketImgAgeStart = redPacketImgAgeStart;
    }

    public Integer getRedPacketImgAgeEnd() {
        return redPacketImgAgeEnd;
    }

    public void setRedPacketImgAgeEnd(Integer redPacketImgAgeEnd) {
        this.redPacketImgAgeEnd = redPacketImgAgeEnd;
    }

    public Date getRedPacketImgDate() {
        return redPacketImgDate;
    }

    public void setRedPacketImgDate(Date redPacketImgDate) {
        this.redPacketImgDate = redPacketImgDate;
    }

    public String getRedPacketImgType() {
        return redPacketImgType;
    }

    public void setRedPacketImgType(String redPacketImgType) {
        this.redPacketImgType = redPacketImgType;
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
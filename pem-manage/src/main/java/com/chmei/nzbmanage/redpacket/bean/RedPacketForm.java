package com.chmei.nzbmanage.redpacket.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 众享红包发布Form
 *
 * @author zhangshixu
 * @since 2019年11月05日 09点25分
 */
public class RedPacketForm implements Serializable {
    /**
     * 众享红包id
     */
    private Long redPacketId;

    /**
     * 用户id
     */
    private String memberAccount;

    /**
     * 红包发布的金额大小
     */
    private BigDecimal redPacketMoneyCount;

    /**
     * 红包发布的个数
     */
    private Integer redPacketCount;

    /**
     * 红包剩余个数
     */
    private Integer redPacketStock;

    /**
     * 红包剩余金额
     */
    private BigDecimal redPacketUnitAmount;

    /**
     * 红包发布的详细信息
     */
    private String redPacketInfo;

    /**
     * 手气最佳人的ID
     */
    private String redPacketLuckStar;

    /**
     * 抢红包完记录开始时间
     */
    private Date redPacketStartTime;

    /**
     * 抢红包完记录结束时间
     */
    private Date redPacketEndTime;

    /**
     * 设置红包题目
     */
    private String redPacketQuestion;

    /**
     * 设置红包题目选项
     */
    private String redPacketOptions;

    /**
     * 设置红包题目答案
     */
    private String redPacketAnswer;

    /**
     * 红包性别
     */
    private Integer redPacketSex;

    /**
     * 红包开始年龄
     */
    private Integer redPacketAgeStart;

    /**
     * 红包结束年龄
     */
    private Integer redPacketAgeEnd;

    /**
     * 红包记录时间
     */
    private Date redPacketDate;

    /**
     * 红包类型（0钱包，1红包，2广告费）
     */
    private String redPacketVideoType;

    /**
     * 抢红包用户ID账号
     */
    private String robUserId;

    private static final long serialVersionUID = 1L;

    public Long getRedPacketId() {
        return redPacketId;
    }

    public void setRedPacketId(Long redPacketId) {
        this.redPacketId = redPacketId;
    }

    public String getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(String memberAccount) {
        this.memberAccount = memberAccount;
    }

    public BigDecimal getRedPacketMoneyCount() {
        return redPacketMoneyCount;
    }

    public void setRedPacketMoneyCount(BigDecimal redPacketMoneyCount) {
        this.redPacketMoneyCount = redPacketMoneyCount;
    }

    public Integer getRedPacketCount() {
        return redPacketCount;
    }

    public void setRedPacketCount(Integer redPacketCount) {
        this.redPacketCount = redPacketCount;
    }

    public Integer getRedPacketStock() {
        return redPacketStock;
    }

    public void setRedPacketStock(Integer redPacketStock) {
        this.redPacketStock = redPacketStock;
    }

    public BigDecimal getRedPacketUnitAmount() {
        return redPacketUnitAmount;
    }

    public void setRedPacketUnitAmount(BigDecimal redPacketUnitAmount) {
        this.redPacketUnitAmount = redPacketUnitAmount;
    }

    public String getRedPacketInfo() {
        return redPacketInfo;
    }

    public void setRedPacketInfo(String redPacketInfo) {
        this.redPacketInfo = redPacketInfo;
    }

    public String getRedPacketLuckStar() {
        return redPacketLuckStar;
    }

    public void setRedPacketLuckStar(String redPacketLuckStar) {
        this.redPacketLuckStar = redPacketLuckStar;
    }

    public Date getRedPacketStartTime() {
        return redPacketStartTime;
    }

    public void setRedPacketStartTime(Date redPacketStartTime) {
        this.redPacketStartTime = redPacketStartTime;
    }

    public Date getRedPacketEndTime() {
        return redPacketEndTime;
    }

    public void setRedPacketEndTime(Date redPacketEndTime) {
        this.redPacketEndTime = redPacketEndTime;
    }

    public String getRedPacketQuestion() {
        return redPacketQuestion;
    }

    public void setRedPacketQuestion(String redPacketQuestion) {
        this.redPacketQuestion = redPacketQuestion;
    }

    public String getRedPacketOptions() {
        return redPacketOptions;
    }

    public void setRedPacketOptions(String redPacketOptions) {
        this.redPacketOptions = redPacketOptions;
    }

    public String getRedPacketAnswer() {
        return redPacketAnswer;
    }

    public void setRedPacketAnswer(String redPacketAnswer) {
        this.redPacketAnswer = redPacketAnswer;
    }

    public Integer getRedPacketSex() {
        return redPacketSex;
    }

    public void setRedPacketSex(Integer redPacketSex) {
        this.redPacketSex = redPacketSex;
    }

    public Integer getRedPacketAgeStart() {
        return redPacketAgeStart;
    }

    public void setRedPacketAgeStart(Integer redPacketAgeStart) {
        this.redPacketAgeStart = redPacketAgeStart;
    }

    public Integer getRedPacketAgeEnd() {
        return redPacketAgeEnd;
    }

    public void setRedPacketAgeEnd(Integer redPacketAgeEnd) {
        this.redPacketAgeEnd = redPacketAgeEnd;
    }

    public Date getRedPacketDate() {
        return redPacketDate;
    }

    public void setRedPacketDate(Date redPacketDate) {
        this.redPacketDate = redPacketDate;
    }

    public String getRedPacketVideoType() {
        return redPacketVideoType;
    }

    public void setRedPacketVideoType(String redPacketVideoType) {
        this.redPacketVideoType = redPacketVideoType;
    }

    public String getRobUserId() {
        return robUserId;
    }

    public void setRobUserId(String robUserId) {
        this.robUserId = robUserId;
    }
}
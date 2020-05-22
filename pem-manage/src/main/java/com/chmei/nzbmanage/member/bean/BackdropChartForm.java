package com.chmei.nzbmanage.member.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

import java.util.Date;

/**
 * t_backdrop_chart
 * @author 
 */
public class BackdropChartForm extends BaseForm {
    /**
     * 主键
     */
    private Long id;

    /**
     * 背景图标题
     */
    private String backdropTitle;

    /**
     * 背景图片地址
     */
    private String backdropUrl;

    /**
     * 背景图描述
     */
    private String remark;

    /**
     * 创建人Id
     */
    private Long crtUserId;

    /**
     * 创建人姓名
     */
    private String crtUserName;

    /**
     * 创建时间
     */
    private Date crtTime;

    /**
     * 修改人Id
     */
    private Long modfUserId;

    /**
     * 修改人姓名
     */
    private String modfUserName;

    /**
     * 修改时间
     */
    private Date modfTime;

    private String startTime;

    private String endTime;

    private Long backdropId;

    private String memberAccount;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBackdropTitle() {
        return backdropTitle;
    }

    public void setBackdropTitle(String backdropTitle) {
        this.backdropTitle = backdropTitle;
    }

    public String getBackdropUrl() {
        return backdropUrl;
    }

    public void setBackdropUrl(String backdropUrl) {
        this.backdropUrl = backdropUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCrtUserId() {
        return crtUserId;
    }

    public void setCrtUserId(Long crtUserId) {
        this.crtUserId = crtUserId;
    }

    public String getCrtUserName() {
        return crtUserName;
    }

    public void setCrtUserName(String crtUserName) {
        this.crtUserName = crtUserName;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public Long getModfUserId() {
        return modfUserId;
    }

    public void setModfUserId(Long modfUserId) {
        this.modfUserId = modfUserId;
    }

    public String getModfUserName() {
        return modfUserName;
    }

    public void setModfUserName(String modfUserName) {
        this.modfUserName = modfUserName;
    }

    public Date getModfTime() {
        return modfTime;
    }

    public void setModfTime(Date modfTime) {
        this.modfTime = modfTime;
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

    public Long getBackdropId() {
        return backdropId;
    }

    public void setBackdropId(Long backdropId) {
        this.backdropId = backdropId;
    }

    public String getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(String memberAccount) {
        this.memberAccount = memberAccount;
    }
}
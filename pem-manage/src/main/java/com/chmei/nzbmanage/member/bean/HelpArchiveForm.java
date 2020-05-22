package com.chmei.nzbmanage.member.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

import java.util.Date;

/**
 * t_help_archive
 * @author 
 */
public class HelpArchiveForm extends BaseForm {
    /**
     * 主键
     */
    private Long id;

    /**
     * 帮助文档标题
     */
    private String helpArchiveTitle;

    /**
     * 帮助文档图片地址
     */
    private String helpArchiveUrl;

    /**
     * 帮助文档描述
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

    /**
     * 附件路径
     */
    private String filePaths;

    private String startTime;

    private String endTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHelpArchiveTitle() {
        return helpArchiveTitle;
    }

    public void setHelpArchiveTitle(String helpArchiveTitle) {
        this.helpArchiveTitle = helpArchiveTitle;
    }

    public String getHelpArchiveUrl() {
        return helpArchiveUrl;
    }

    public void setHelpArchiveUrl(String helpArchiveUrl) {
        this.helpArchiveUrl = helpArchiveUrl;
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

    public String getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(String filePaths) {
        this.filePaths = filePaths;
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
}
package com.chmei.nzbmanage.complaint.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

import java.util.Date;

/**
 * 会员投诉管理Form
 *
 * @author zhangshixu
 * @since 2019年10月28日 16点14分
 */
public class MemberComplaintForm extends BaseForm {
    /**
     * 主键
     */
    private Long id;

    /**
     * 会员账号
     */
    private String memberAccount;

    /**
     * 会员昵称
     */
    private String memberNickname;

    /**
     * 投诉人
     */
    private String complainant;

    /**
     * 投诉时间
     */
    private Date complaintTime;

    /**
     * 投诉原因
     */
    private String complaintRemark;

    /**
     * 聊天记录
     */
    private String chatRecord;

    /**
     * 投诉状态（1001待处理，1002已忽略，1003警告，1004冻结，1005解冻）
     */
    private String status;

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
    private Date modfUserTime;

    /**
     * 审核意见
     */
    private String auditOpinion;

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

    public String getMemberNickname() {
        return memberNickname;
    }

    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname;
    }

    public String getComplainant() {
        return complainant;
    }

    public void setComplainant(String complainant) {
        this.complainant = complainant;
    }

    public Date getComplaintTime() {
        return complaintTime;
    }

    public void setComplaintTime(Date complaintTime) {
        this.complaintTime = complaintTime;
    }

    public String getComplaintRemark() {
        return complaintRemark;
    }

    public void setComplaintRemark(String complaintRemark) {
        this.complaintRemark = complaintRemark;
    }

    public String getChatRecord() {
        return chatRecord;
    }

    public void setChatRecord(String chatRecord) {
        this.chatRecord = chatRecord;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Date getModfUserTime() {
        return modfUserTime;
    }

    public void setModfUserTime(Date modfUserTime) {
        this.modfUserTime = modfUserTime;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }
}
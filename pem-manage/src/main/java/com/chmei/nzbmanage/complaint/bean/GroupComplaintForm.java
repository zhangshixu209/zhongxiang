package com.chmei.nzbmanage.complaint.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

import java.util.Date;

/**
 * 群投诉管理Form
 *
 * @author zhangshixu
 * @since 2019年10月28日 16点13分
 */
public class GroupComplaintForm extends BaseForm {
    /**
     * 主键
     */
    private Long id;

    /**
     * 群ID
     */
    private String groupId;

    /**
     * 群主账号
     */
    private String ownerAccount;

    /**
     * 群主昵称
     */
    private String ownerNickname;

    /**
     * 群聊名称
     */
    private String groupChatName;

    /**
     * 群容量
     */
    private String groupCapacity;

    /**
     * 会员人数
     */
    private String memberNum;

    /**
     * 群创建时间
     */
    private Date groupCrtTime;

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
     * 群聊记录
     */
    private String groupChatRecord;

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

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getOwnerAccount() {
        return ownerAccount;
    }

    public void setOwnerAccount(String ownerAccount) {
        this.ownerAccount = ownerAccount;
    }

    public String getOwnerNickname() {
        return ownerNickname;
    }

    public void setOwnerNickname(String ownerNickname) {
        this.ownerNickname = ownerNickname;
    }

    public String getGroupChatName() {
        return groupChatName;
    }

    public void setGroupChatName(String groupChatName) {
        this.groupChatName = groupChatName;
    }

    public String getGroupCapacity() {
        return groupCapacity;
    }

    public void setGroupCapacity(String groupCapacity) {
        this.groupCapacity = groupCapacity;
    }

    public String getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(String memberNum) {
        this.memberNum = memberNum;
    }

    public Date getGroupCrtTime() {
        return groupCrtTime;
    }

    public void setGroupCrtTime(Date groupCrtTime) {
        this.groupCrtTime = groupCrtTime;
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

    public String getGroupChatRecord() {
        return groupChatRecord;
    }

    public void setGroupChatRecord(String groupChatRecord) {
        this.groupChatRecord = groupChatRecord;
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

}
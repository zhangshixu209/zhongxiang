package com.chmei.nzbmanage.cashaudit.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

import java.util.Date;

/**
 * 轮播图管理Form
 *
 * @author zhangshixu
 * @since 2019年10月23日 09点29分
 */
public class CashAuditForm extends BaseForm {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 提现用户ID
     */
    private String memberAccount;

    /**
     * 提现用户姓名
     */
    private String cashUserName;

    /**
     * 账户类型（1001支付宝，1002微信，1003银行卡）
     */
    private String accountType;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 提现金额
     */
    private String cashAmount;

    /**
     * 提现申请时间
     */
    private Date cashApplyTime;

    /**
     * 审核状态（1审核通过，2审核不通过，3待审核）
     */
    private String auditType;

    /**
     * 审核意见
     */
    private String auditOpinion;

    /**
     * 审核时间
     */
    private Date auditTime;

    /**
     * 审核人姓名
     */
    private String auditUserName;

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

    private String startTime;

    private String endTime;

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

    public String getCashUserName() {
        return cashUserName;
    }

    public void setCashUserName(String cashUserName) {
        this.cashUserName = cashUserName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(String cashAmount) {
        this.cashAmount = cashAmount;
    }

    public Date getCashApplyTime() {
        return cashApplyTime;
    }

    public void setCashApplyTime(Date cashApplyTime) {
        this.cashApplyTime = cashApplyTime;
    }

    public String getAuditType() {
        return auditType;
    }

    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditUserName() {
        return auditUserName;
    }

    public void setAuditUserName(String auditUserName) {
        this.auditUserName = auditUserName;
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
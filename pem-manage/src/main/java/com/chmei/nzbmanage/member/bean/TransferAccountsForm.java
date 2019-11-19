package com.chmei.nzbmanage.member.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

/**
 * 众享会员转账封装类
 *
 * @author zhangshixu
 * @since 2019年11月13日 15点26分
 */
public class TransferAccountsForm extends BaseForm {

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    /**
     * 会员账号
     */
    private String memberAccount;

    /**
     * 好友账号
     */
    private String friendMemberAccount;

    /**
     * 转账金额
     */
    private Double transferMoney;

    /**
     * 会员资产类型（钱包0，广告费1）
     */
    private String accountType;

    public String getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(String memberAccount) {
        this.memberAccount = memberAccount;
    }

    public String getFriendMemberAccount() {
        return friendMemberAccount;
    }

    public void setFriendMemberAccount(String friendMemberAccount) {
        this.friendMemberAccount = friendMemberAccount;
    }

    public Double getTransferMoney() {
        return transferMoney;
    }

    public void setTransferMoney(Double transferMoney) {
        this.transferMoney = transferMoney;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}

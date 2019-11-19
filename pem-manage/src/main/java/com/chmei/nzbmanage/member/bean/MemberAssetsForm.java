package com.chmei.nzbmanage.member.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

/**
 * 会员资金管理封装类
 *
 * @author zhangshixu
 * @since 2019年11月24日 15点26分
 */
public class MemberAssetsForm extends BaseForm {

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 会员账号
     */
    private String memberAccount;

    /**
     * 会员资产类型（钱包1001，红包1002，广告费1003）
     */
    private String assetsType;

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

    public String getAssetsType() {
        return assetsType;
    }

    public void setAssetsType(String assetsType) {
        this.assetsType = assetsType;
    }
}

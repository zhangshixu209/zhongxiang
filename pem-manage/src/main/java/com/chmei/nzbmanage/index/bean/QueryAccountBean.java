package com.chmei.nzbmanage.index.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

/**
 * 管理员端首页查询待审核用户bean
 * Date:     2018年12月12日 上午10:31:56
 * @author   tongym
 * @version
 * @since    JDK 1.8
 */
public class QueryAccountBean extends BaseForm {
    private String memberState;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberState() {
        return memberState;
    }

    public void setMemberState(String memberState) {
        this.memberState = memberState;
    }
}

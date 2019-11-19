package com.chmei.nzbmanage.right.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

/**  
 * 查询管理员条件Form
 * Date:     2018年8月13日 上午11:07:40 
 * @author   lianziyu  
 * @since    JDK 1.7  
 */
public class AdminQueryForm extends BaseForm {

	private static final long serialVersionUID = 1L;
	
	private String userName;
	
	private String loginId;
	
	private String roleId;
	
	private String userDepartment;

	public String getUserDepartment() {
        return userDepartment;
    }

    public void setUserDepartment(String userDepartment) {
        this.userDepartment = userDepartment;
    }

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
}
  

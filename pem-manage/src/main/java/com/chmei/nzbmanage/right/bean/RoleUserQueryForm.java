package com.chmei.nzbmanage.right.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

/** 
 * 角色用户查询form 
 * Date:     2018年8月15日 上午11:29:26 
 * @author   lianziyu  
 * @version    
 * @since    JDK 1.7  
 */
public class RoleUserQueryForm extends BaseForm {

	private static final long serialVersionUID = 1L;
	//角色Id
	private String roleId;
	//角色Id
	private String sysTypeCd;
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getSysTypeCd() {
		return sysTypeCd;
	}

	public void setSysTypeCd(String sysTypeCd) {
		this.sysTypeCd = sysTypeCd;
	}

}
  

package com.chmei.nzbmanage.right.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

/**  
 * 发包方管理员查询form
 * Date:     2018年8月22日 下午2:24:17 
 * @author   lianziyu  
 * @version    
 * @since    JDK 1.7  
 */
public class ErAdminQueryForm extends BaseForm {

	private static final long serialVersionUID = 1L;
	/** 手机号 */
	private String mobile;
	/** 姓名 */
	private String realName;
	/** 用户状态 */
	private String erUserState;
	/** 角色Id */
	private String roleId;
	/** 组织名 */
	private String orgName;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getErUserState() {
		return erUserState;
	}
	public void setErUserState(String erUserState) {
		this.erUserState = erUserState;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
}
  

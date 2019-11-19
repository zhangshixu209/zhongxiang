
package com.chmei.nzbmanage.right.bean;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**  
 * 修改角色权限
 * Date:     2018年8月14日 下午4:22:17 
 * @author   lianziyu  
 * @version    
 * @since    JDK 1.7  
 */
public class RoleRightEditForm implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 角色ID*/
	@NotNull
	private String roleId;
	/**权限Id,多个以，分割*/
	private String rightIds;
	/**报表分类Id,多个以，分割*/
	private String reportTypeId;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRightIds() {
		return rightIds;
	}
	public void setRightIds(String rightIds) {
		this.rightIds = rightIds;
	}
	public String getReportTypeId() {
		return reportTypeId;
	}
	public void setReportTypeId(String reportTypeId) {
		this.reportTypeId = reportTypeId;
	}
	
	public RoleRightEditForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RoleRightEditForm(String roleId, String rightIds, String reportTypeId) {
		super();
		this.roleId = roleId;
		this.rightIds = rightIds;
		this.reportTypeId = reportTypeId;
	}
	@Override
	public String toString() {
		return "RoleRightEditForm [roleId=" + roleId + ", rightIds=" + rightIds + ", reportTypeId=" + reportTypeId
				+ "]";
	}
	
	
	
	
}
  


package com.chmei.nzbmanage.right.bean;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**  
 * 修改角色用户关联
 * Date:     2018年8月13日 下午5:55:46 
 * @author   lianziyu  
 * @version    
 * @since    JDK 1.7  
 */
public class RoleUserEditForm implements Serializable{

	private static final long serialVersionUID = 1L;
	//角色Id
	@NotNull
	private String roleId;
	//新增的用户Ids
	private String addUserIds;
	//删除的用户Ids
	private String removeUserIds;
	 /**1:管理端,2:接包方端,3:发包方端*/
    @NotNull
    private String sysTypeCd;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getAddUserIds() {
		return addUserIds;
	}
	public void setAddUserIds(String addUserIds) {
		this.addUserIds = addUserIds;
	}
	public String getRemoveUserIds() {
		return removeUserIds;
	}
	public void setRemoveUserIds(String removeUserIds) {
		this.removeUserIds = removeUserIds;
	}
	public String getSysTypeCd() {
		return sysTypeCd;
	}
	public void setSysTypeCd(String sysTypeCd) {
		this.sysTypeCd = sysTypeCd;
	}
	
}
  

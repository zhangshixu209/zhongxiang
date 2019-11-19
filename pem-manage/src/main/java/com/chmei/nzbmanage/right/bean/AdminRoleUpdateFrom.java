
package com.chmei.nzbmanage.right.bean;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/** 
 * 管理员权限修改Form 
 * Date:     2018年8月24日 下午2:47:02 
 * @author   lianziyu  
 * @version    
 * @since    JDK 1.7  
 */
public class AdminRoleUpdateFrom implements Serializable{

	private static final long serialVersionUID = 1L;
	/**用户Id*/
	@NotNull
	private String userId;
	/**新增的角色，多个角色Id以,分割*/
	private String addRoleIds;
	/**去除的角色，多个角色Id以,分割*/
	private String removeRoleIds;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAddRoleIds() {
		return addRoleIds;
	}
	public void setAddRoleIds(String addRoleIds) {
		this.addRoleIds = addRoleIds;
	}
	public String getRemoveRoleIds() {
		return removeRoleIds;
	}
	public void setRemoveRoleIds(String removeRoleIds) {
		this.removeRoleIds = removeRoleIds;
	}
	
}
  


package com.chmei.nzbmanage.right.bean;  
/**  
 * 角色权限查询form
 * Date:     2018年8月22日 上午11:36:10 
 * @author   lianziyu  
 * @version    
 * @since    JDK 1.7  
 */

import java.io.Serializable;

public class RoleRightQueryForm implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**角色Id*/
	private String roleId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	
	
}
  

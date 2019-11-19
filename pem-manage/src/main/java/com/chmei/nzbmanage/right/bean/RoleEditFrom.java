
package com.chmei.nzbmanage.right.bean;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**  
 * 角色编辑Form
 * Date:     2018年8月10日 下午5:26:35 
 * @author   lianziyu  
 * @version    
 * @since    JDK 1.7  
 */
public class RoleEditFrom implements Serializable{

private static final long serialVersionUID = 1L;

	/**id*/
	@NotNull
	private String id;
	/**名称 */
	@NotNull
	private String roleName;
	/**描述 */
	private String description;
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
  

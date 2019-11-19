
package com.chmei.nzbmanage.right.bean;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**  
 * 角色添加表单
 * Date:     2018年8月9日 下午4:14:23 
 * @author   dell  
 * @version    
 * @since    JDK 1.7  
 */
public class RoleAddForm implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**名称 */
	@NotNull
	private String roleName;
	/**是否内置:0-是，1-否*/
	private String isinner;
	/**描述 */
	private String description;
	/**有效状态:1表示有效，0表示无效 */
	private String validStsCd;
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getIsinner() {
		return isinner;
	}
	public void setIsinner(String isinner) {
		this.isinner = isinner;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getValidStsCd() {
		return validStsCd;
	}
	public void setValidStsCd(String validStsCd) {
		this.validStsCd = validStsCd;
	}
}
  

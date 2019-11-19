package com.chmei.nzbmanage.right.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

/** 
 * 角色查询form 
 * Date:     2018年8月9日 下午4:09:49 
 * @author   lianziyu  
 * @version    
 * @since    JDK 1.7  
 */
public class RoleQueryForm extends BaseForm {

	private static final long serialVersionUID = 1L;
	
	/**角色名称*/
	private String roleName;
	//用户Id
	private String userId;
	
	private Integer pageNum;
	private Integer pageSize;
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
  

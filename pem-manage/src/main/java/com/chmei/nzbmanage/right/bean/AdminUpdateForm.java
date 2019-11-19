
package com.chmei.nzbmanage.right.bean;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/** 
 * 管理员修改from 
 * Date:     2018年8月23日 下午3:38:37 
 * @author   lianziyu  
 * @version    
 * @since    JDK 1.7  
 */
public class AdminUpdateForm implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	private String id;
	/** 手机号 */
	@NotNull
	@Length(min=11,max=11,message="手机号长度不正确")
	private String loginId;
	/** 用户名 */
	@NotNull
	private String userName;
	@NotNull
	/** 邮箱 */
	private String email;
	@NotNull
	/** 性别 */
	private String sex;
	/** 密码 */
	@NotNull
	private String loginPw;
	/** 状态 */
	@NotNull
	private Integer validStsCd;
	/** 部门id */
    @NotNull
    private String userDepartment;
    /**
     * 部门名字
     */
    @NotNull
    private String userDepartmentName;
    
	public String getUserDepartment() {
        return userDepartment;
    }
    public void setUserDepartment(String userDepartment) {
        this.userDepartment = userDepartment;
    }
    public String getUserDepartmentName() {
        return userDepartmentName;
    }
    public void setUserDepartmentName(String userDepartmentName) {
        this.userDepartmentName = userDepartmentName;
    }
    public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getLoginPw() {
		return loginPw;
	}
	public void setLoginPw(String loginPw) {
		this.loginPw = loginPw;
	}
	public Integer getValidStsCd() {
		return validStsCd;
	}
	public void setValidStsCd(Integer validStsCd) {
		this.validStsCd = validStsCd;
	}
	
}
  

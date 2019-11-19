
package com.chmei.nzbmanage.right.bean;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**  
 * 管理端管理员添加form
 * Date:     2018年8月23日 下午3:31:56 
 * @author   lianziyu  
 * @version    
 * @since    JDK 1.7  
 */
public class AdminAddForm implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** 手机号 */
	@NotNull
	@Length(min=11,max=11,message="手机号长度不正确")
	private String loginId;
	/** 密码 */
	@NotNull
	private String loginPw;
	/** 用户名 */
	@NotNull
	private String userName;
	/** 邮箱 */
	@NotNull
	private String userDepartment;
	/**
	 * 部门名字
	 */
	@NotNull
	private String userDepartmentName;
	/** 性别 */
	@NotNull
	private String sex;
	/** 状态 */
	@NotNull
	private Integer validStsCd;
	/** 身份证号 */
	@NotNull
	private String extend1;
	
	
	public String getExtend1() {
		return extend1;
	}
	public void setExtend1(String extend1) {
		this.extend1 = extend1;
	}
	public String getUserDepartmentName() {
        return userDepartmentName;
    }
    public void setUserDepartmentName(String userDepartmentName) {
        this.userDepartmentName = userDepartmentName;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserDepartment() {
		return userDepartment;
	}
	public void setUserDepartment(String userDepartment) {
		this.userDepartment = userDepartment;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getValidStsCd() {
		return validStsCd;
	}
	public void setValidStsCd(Integer validStsCd) {
		this.validStsCd = validStsCd;
	}
	public AdminAddForm(String loginId, String loginPw, String userName, String userDepartment, String sex,
			Integer validStsCd) {
		super();
		this.loginId = loginId;
		this.loginPw = loginPw;
		this.userName = userName;
		this.userDepartment = userDepartment;
		this.sex = sex;
		this.validStsCd = validStsCd;
	}
	public AdminAddForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "AdminAddForm [loginId=" + loginId + ", loginPw=" + loginPw + ", userName=" + userName
				+ ", userDepartment=" + userDepartment + ", sex=" + sex + ", validStsCd=" + validStsCd + "]";
	}
	
	

	
}
  

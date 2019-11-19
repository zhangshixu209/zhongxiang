package com.chmei.nzbmanage.right.bean;

/**
 * <p>用户查询
 * @author lixinjie
 * @since 2018-12-12
 */
public class UserQueryForm {

	private Long id;
	private Integer sysTypeCd;
	private String mobile;
	private String realName;
	private Integer pageNum;
	private Integer pageSize;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getSysTypeCd() {
		return sysTypeCd;
	}
	public void setSysTypeCd(Integer sysTypeCd) {
		this.sysTypeCd = sysTypeCd;
	}
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

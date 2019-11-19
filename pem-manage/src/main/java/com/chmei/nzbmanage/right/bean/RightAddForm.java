
package com.chmei.nzbmanage.right.bean;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**  
 * 添加权限表单
 * Date:     2018年8月9日 下午3:17:36 
 * @author   lianziyu  
 * @since    JDK 1.7  
 */
public class RightAddForm implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	/**权限名称*/
	@NotNull
    private String name;
    /**父Id*/
    private String parentId;
    /**权限描述*/
    private String description;
    /** 操作系统Id*/
    private String appSysId;
    /**有效标识，1表示有效，0表示无效*/
    private String validFlag;
    /**权限类型:menu表示菜单，button表示按钮，link表示链接*/
    @NotNull
    private String typeCd;
    /**权限代码*/
    private String uiPrivCd;
    /**校验url参数标志:0表示校验，1表示不校验*/
    @NotNull
    private String tsvldUrlParaFlag;
    /**url地址*/
    private String urlAddr;
    /**order_level*/
    @NotNull
    private String orderLevel;
    /**mo值,用于匹配链接菜单的链接或按钮的code,对应md5加密*/
    private String mo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAppSysId() {
		return appSysId;
	}
	public void setAppSysId(String appSysId) {
		this.appSysId = appSysId;
	}
	public String getValidFlag() {
		return validFlag;
	}
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}
	public String getTypeCd() {
		return typeCd;
	}
	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}
	public String getUiPrivCd() {
		return uiPrivCd;
	}
	public void setUiPrivCd(String uiPrivCd) {
		this.uiPrivCd = uiPrivCd;
	}
	public String getTsvldUrlParaFlag() {
		return tsvldUrlParaFlag;
	}
	public void setTsvldUrlParaFlag(String tsvldUrlParaFlag) {
		this.tsvldUrlParaFlag = tsvldUrlParaFlag;
	}
	public String getUrlAddr() {
		return urlAddr;
	}
	public void setUrlAddr(String urlAddr) {
		this.urlAddr = urlAddr;
	}
	public String getOrderLevel() {
		return orderLevel;
	}
	public void setOrderLevel(String orderLevel) {
		this.orderLevel = orderLevel;
	}
	public String getMo() {
		return mo;
	}
	public void setMo(String mo) {
		this.mo = mo;
	}
	public RightAddForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RightAddForm(Long id, String name, String parentId, String description, String appSysId, String validFlag,
			String typeCd, String uiPrivCd, String tsvldUrlParaFlag, String urlAddr, String orderLevel, String mo) {
		super();
		this.id = id;
		this.name = name;
		this.parentId = parentId;
		this.description = description;
		this.appSysId = appSysId;
		this.validFlag = validFlag;
		this.typeCd = typeCd;
		this.uiPrivCd = uiPrivCd;
		this.tsvldUrlParaFlag = tsvldUrlParaFlag;
		this.urlAddr = urlAddr;
		this.orderLevel = orderLevel;
		this.mo = mo;
	}
	@Override
	public String toString() {
		return "RightAddForm [id=" + id + ", name=" + name + ", parentId=" + parentId + ", description=" + description
				+ ", appSysId=" + appSysId + ", validFlag=" + validFlag + ", typeCd=" + typeCd + ", uiPrivCd="
				+ uiPrivCd + ", tsvldUrlParaFlag=" + tsvldUrlParaFlag + ", urlAddr=" + urlAddr + ", orderLevel="
				+ orderLevel + ", mo=" + mo + "]";
	}
	
    
}
  

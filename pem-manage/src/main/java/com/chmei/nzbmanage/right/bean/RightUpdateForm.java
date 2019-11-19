
package com.chmei.nzbmanage.right.bean;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**  
 * 权限修改Form
 * Date:     2018年8月10日 上午10:55:14 
 * @author   lianziyu  
 * @version    
 * @since    JDK 1.7  
 */
public class RightUpdateForm implements Serializable{
	private static final long serialVersionUID = 1L;
	@NotNull
	private Long id;
	/**权限名称*/
	@NotNull
    private String name;
    /**权限描述*/
    private String description;
    /**权限类型:menu表示菜单，button表示按钮，link表示链接*/
    @NotNull
    private String typeCd;
    /**权限代码*/
    private String uiPrivCd;
    /**校验url参数标志:0表示校验，1表示不校验*/
    @NotNull
    private String tsvldUrlParaFlag;
    /**url地址*/
    @NotNull
    private String urlAddr;
    /**order_level*/
    @NotNull
    private String orderLevel;
    /**mo值,用于匹配链接菜单的链接或按钮的code,对应md5加密*/
    private String mo;
    /**1:管理端,2:接包方端,3:发包方端*/
    @NotNull
    private String sysTypeCd;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getSysTypeCd() {
		return sysTypeCd;
	}
	public void setSysTypeCd(String sysTypeCd) {
		this.sysTypeCd = sysTypeCd;
	}
    
}
  

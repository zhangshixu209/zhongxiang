
package com.chmei.nzbmanage.right.bean;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**  
 * Date:     2018年8月28日 下午1:13:29 
 * @author   lianziyu  
 * @version    
 * @since    JDK 1.7  
 */
public class RightDeleteForm implements Serializable{

	private static final long serialVersionUID = 1L;
	@NotNull
	private Long id;
	/**1:管理端,2:接包方端,3:发包方端*/
    @NotNull
    private String sysTypeCd;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSysTypeCd() {
		return sysTypeCd;
	}
	public void setSysTypeCd(String sysTypeCd) {
		this.sysTypeCd = sysTypeCd;
	}
}
  

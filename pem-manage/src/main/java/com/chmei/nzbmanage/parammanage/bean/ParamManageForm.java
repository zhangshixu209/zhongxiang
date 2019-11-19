package com.chmei.nzbmanage.parammanage.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

import java.util.Date;

/**
 * 参数管理和字典表配置Form
 *
 * @author 翟超锋
 * @since 2019年05月07日 15点20分
 */
public class ParamManageForm extends BaseForm {
    /** 序列化ID*/
    private static final long serialVersionUID = 7194373025312398122L;
    /**
     * id
     **/
    private String id;
    /**
     * 字典值
     **/
    private String paramName;
    /**
     * 字典键
     **/
    private String paramKey;
    /**
     * 预留1
     **/
    private String ext1;
    /**
     * 预留2
     **/
    private String ext2;
    /**
     * 预留3
     **/
    private String ext3;
    /**
     * 状态 1有效0删除
     **/
    private String status;
    /**
     * 字典值
     **/
    private String remark;
    /**
     * 创建人id
     **/
    private String crtId;
    /**
     * 创建时间
     **/
    private Date crtTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamKey() {
		return paramKey;
	}
	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public String getExt3() {
		return ext3;
	}
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCrtId() {
		return crtId;
	}
	public void setCrtId(String crtId) {
		this.crtId = crtId;
	}
	public Date getCrtTime() {
		return crtTime;
	}
	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}
	public ParamManageForm(String id, String paramName, String paramKey, String ext1, String ext2, String ext3,
			String status, String remark, String crtId, Date crtTime) {
		super();
		this.id = id;
		this.paramName = paramName;
		this.paramKey = paramKey;
		this.ext1 = ext1;
		this.ext2 = ext2;
		this.ext3 = ext3;
		this.status = status;
		this.remark = remark;
		this.crtId = crtId;
		this.crtTime = crtTime;
	}
	public ParamManageForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ParamManageForm [id=" + id + ", paramName=" + paramName + ", paramKey=" + paramKey + ", ext1=" + ext1
				+ ", ext2=" + ext2 + ", ext3=" + ext3 + ", status=" + status + ", remark=" + remark + ", crtId=" + crtId
				+ ", crtTime=" + crtTime + "]";
	}
    
}

package com.chmei.nzbmanage.right.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

import java.util.Date;

/**
 * 数据库备份/还原管理Form
 *
 * @author zhangshixu
 * @since 2019年10月21日 16点52分
 */
public class SysSqlForm extends BaseForm {
	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 链接地址
	 */
	private String sqlUrl;

	/**
	 * 在线状态（1备份，2还原）
	 */
	private String optType;

	/**
	 * 创建人Id
	 */
	private Long crtUserId;

	/**
	 * 创建人姓名
	 */
	private String crtUserName;

	/**
	 * 创建时间
	 */
	private Date crtTime;

	/**
	 * 修改人Id
	 */
	private Long modfUserId;

	/**
	 * 修改人姓名
	 */
	private String modfUserName;

	/**
	 * 修改时间
	 */
	private Date modfTime;

	/**
	 * 附件路径
	 */
	private String filePaths;

	private String remark;

	private static final long serialVersionUID = 1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSqlUrl() {
		return sqlUrl;
	}

	public void setSqlUrl(String sqlUrl) {
		this.sqlUrl = sqlUrl;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public Long getCrtUserId() {
		return crtUserId;
	}

	public void setCrtUserId(Long crtUserId) {
		this.crtUserId = crtUserId;
	}

	public String getCrtUserName() {
		return crtUserName;
	}

	public void setCrtUserName(String crtUserName) {
		this.crtUserName = crtUserName;
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	public Long getModfUserId() {
		return modfUserId;
	}

	public void setModfUserId(Long modfUserId) {
		this.modfUserId = modfUserId;
	}

	public String getModfUserName() {
		return modfUserName;
	}

	public void setModfUserName(String modfUserName) {
		this.modfUserName = modfUserName;
	}

	public Date getModfTime() {
		return modfTime;
	}

	public void setModfTime(Date modfTime) {
		this.modfTime = modfTime;
	}

	public String getFilePaths() {
		return filePaths;
	}

	public void setFilePaths(String filePaths) {
		this.filePaths = filePaths;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
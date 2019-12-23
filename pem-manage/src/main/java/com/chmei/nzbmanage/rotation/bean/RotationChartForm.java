package com.chmei.nzbmanage.rotation.bean;

import com.chmei.nzbmanage.common.controller.BaseForm;

import java.util.Date;

/**
 * 轮播图管理Form
 *
 * @author zhangshixu
 * @since 2019年10月21日 16点52分
 */
public class RotationChartForm extends BaseForm {
	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 轮播图ID
	 */
	private Long rotationChartId;

	/**
	 * 链接地址
	 */
	private String linkAddress;

	/**
	 * 在线状态（1在线，2下线）
	 */
	private String onlineStatus;

	/**
	 * 轮播图描述
	 */
	private String remark;

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

	private String startTime;

	private String endTime;

	private static final long serialVersionUID = 1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRotationChartId() {
		return rotationChartId;
	}

	public void setRotationChartId(Long rotationChartId) {
		this.rotationChartId = rotationChartId;
	}

	public String getLinkAddress() {
		return linkAddress;
	}

	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}

	public String getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
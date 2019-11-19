package com.chmei.nzbmanage.log.bean;

import org.springframework.format.annotation.DateTimeFormat;

import com.chmei.nzbmanage.common.controller.BaseForm;

/**
 * 操作日志分页查询入参
 * Title: OperationLogQueryListForm
 * Description:
 * @author gaoxuemin
 * @date 2018年9月6日
 */
public class OperationLogQueryListForm extends BaseForm {

	private static final long serialVersionUID = -5932181189541406962L;
	// 所属平台:1:平台管理端,2:坐席端,3:发包商,4:接包商
	private String itemBelong;
	// 操作用户姓名
	private String operationUserName;
	// 操作对象
	private String operationModel;
	// 操作用户手机号
	private String operationUserMobile;

	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private String timeBegin;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private String timeEnd;

	public String getItemBelong() {
		return itemBelong;
	}

	public void setItemBelong(String itemBelong) {
		this.itemBelong = itemBelong;
	}

	public String getOperationUserName() {
		return operationUserName;
	}

	public void setOperationUserName(String operationUserName) {
		this.operationUserName = operationUserName;
	}

	public String getOperationModel() {
		return operationModel;
	}

	public void setOperationModel(String operationModel) {
		this.operationModel = operationModel;
	}

	public String getOperationUserMobile() {
		return operationUserMobile;
	}

	public void setOperationUserMobile(String operationUserMobile) {
		this.operationUserMobile = operationUserMobile;
	}

	public String getTimeBegin() {
		return timeBegin;
	}

	public void setTimeBegin(String timeBegin) {
		this.timeBegin = timeBegin;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

}

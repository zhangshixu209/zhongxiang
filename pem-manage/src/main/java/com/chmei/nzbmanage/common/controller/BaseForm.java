package com.chmei.nzbmanage.common.controller;

import java.io.Serializable;

public class BaseForm implements Serializable{

	private static final long serialVersionUID = 2510323512091199446L;

	private Integer pageNumber=1; // 当前页数
	
	private Integer limit=10; // 每页显示记录数
	
	private Integer start; //分页开始数
	

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		if(limit!=null&&limit>10) {
			this.limit = 10;
		}else {
			this.limit = limit;
		}
	}

}

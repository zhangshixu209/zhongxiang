package com.chmei.nzbmanage.log.bean;

import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;

public class OperationLogQueryDescribeForm implements Serializable {

	private static final long serialVersionUID = 5221743341617638007L;
	
	private Long id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private String crtTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(String crtTime) {
		this.crtTime = crtTime;
	}

}

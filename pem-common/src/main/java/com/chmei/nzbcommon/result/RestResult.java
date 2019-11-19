package com.chmei.nzbcommon.result;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * @author lixinjie
 * @since 2018-08-08
 */
@JsonRootName("result")
public class RestResult {

	private int code;
	private String desc;
	@JsonInclude(Include.NON_NULL)
	private List<?> list;
	@JsonInclude(Include.NON_NULL)
	private Long total;
	@JsonInclude(Include.NON_NULL)
	private Object data;
	
	public RestResult(int code, String desc) {
		this(code, desc, null);
	}
	
	public RestResult(int code, String desc, Object data) {
		this(code, desc, null, null, data);
	}
	
	public RestResult(int code, String desc, List<?> list, Long total) {
		this(code, desc, list, total, null);
	}
	
	public RestResult(int code, String desc, List<?> list, Long total, Object data) {
		this.code = code;
		this.desc = desc;
		this.list = list;
		this.total = total;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public List<?> getList() {
		return list;
	}

	public Long getTotal() {
		return total;
	}

	public Object getData() {
		return data;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}

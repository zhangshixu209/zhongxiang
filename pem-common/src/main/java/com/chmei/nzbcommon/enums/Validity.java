package com.chmei.nzbcommon.enums;

/**
 * 有效性状态
 * @author lixinjie
 * @since 2018-12-03
 */
public enum Validity {

	INVALID(0, "无效"),
	VALID(1, "有效");
	
	private Integer code;
	private String name;
	
	private Validity(Integer code, String name) {
		this.code = code;
		this.name= name;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
	public Validity parse(Integer code) {
		for (Validity v : values()) {
			if (v.code.equals(code)) {
				return v;
			}
		}
		throw new IllegalArgumentException("Validity code '" + code + "' is invalid.");
	}
}

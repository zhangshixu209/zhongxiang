package com.chmei.nzbcommon.sex;

/**
 * @author lixinjie
 * @since 2018-10-23
 */
public enum Sex {

	Male(0, "男"),
	Female(1, "女");
	
	private Integer code;
	private String name;
	
	private Sex(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public static final Sex valueOf(Integer code) {
		for (Sex sex : values()) {
			if (sex.code == code) {
				return sex;
			}
		}
		throw new IllegalArgumentException("Sex code '" + code + "' is invalid.");
	}
}

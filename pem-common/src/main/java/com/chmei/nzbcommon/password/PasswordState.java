package com.chmei.nzbcommon.password;

/**
 * @author lixinjie
 * @since 2018-10-21
 */
public enum PasswordState {

	Normal(0, "正常"),
	Expired(1, "过期");
	
	private Integer code;
	private String name;
	
	private PasswordState(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public static PasswordState valueOf(Integer code) {
		for (PasswordState ps : values()) {
			if (ps.code == code) {
				return ps;
			}
		}
		throw new IllegalArgumentException("PasswordState code '" + code + "' is invalid.");
	}
}

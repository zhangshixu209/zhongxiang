package com.chmei.nzbcommon.auth;

/**
 * @author lixinjie
 * @since 2018-10-24
 */
public enum AuthType {

	Menu(0, "菜单"),
	Button(1, "按钮");
	
	private Integer code;
	private String name;
	
	private AuthType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public static final AuthType valueOf(Integer code) {
		for (AuthType authType : values()) {
			if (authType.code == code) {
				return authType;
			}
		}
		throw new IllegalArgumentException("auth code '" + code + "' is invalid.");
	}
}

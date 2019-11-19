package com.chmei.nzbcommon.enums;

/**
 * <p>权限类型
 * @author lixinjie
 * @since 2018-12-03
 */
public enum PrivType {

	MENU("menu", "菜单"),
	LINK("link", "链接"),
	BUTTON("button", "按钮");
	
	private String code;
	private String name;
	
	private PrivType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public static PrivType parse(String code) {
		for (PrivType pt : values()) {
			if (pt.code.equals(code)) {
				return pt;
			}
		}
		throw new IllegalArgumentException("PrivType code '" + code + "' is invalid.");
	}
}
